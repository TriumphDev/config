package me.mattstudios.config.resource;

import me.mattstudios.config.configurationdata.ConfigurationData;
import me.mattstudios.config.exception.ConfigMeException;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.resource.PropertyPathTraverser.PathElement;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class YamlFileResource implements PropertyResource {

    private final Path path;
    private final YamlFileResourceOptions options;
    private final String indentationSpace;
    private Yaml yamlObject;

    public YamlFileResource(Path path) {
        this(path, YamlFileResourceOptions.builder().build());
    }

    public YamlFileResource(Path path, YamlFileResourceOptions options) {
        this.path = path;
        this.options = options;
        this.indentationSpace = String.join("", Collections.nCopies(options.getIndentationSize(), " "));
    }

    /**
     * Constructor (legacy). Prefer {@link #YamlFileResource(Path)}.
     *
     * @param file the file
     * @deprecated scheduled for removal
     */
    @Deprecated
    public YamlFileResource(File file) {
        this(file.toPath());
    }

    @Override
    public PropertyReader createReader() {
        return new YamlFileReader(path, options.getCharset());
    }

    @Override
    public void exportProperties(ConfigurationData configurationData) {
        try (OutputStream os = Files.newOutputStream(path);
             OutputStreamWriter writer = new OutputStreamWriter(os, options.getCharset())) {
            PropertyPathTraverser pathTraverser = new PropertyPathTraverser(configurationData);

            writeDescription(writer, configurationData.getCommentsForSection("TH-CONFIG-DESCRIPTION"));

            for (Property<?> property : configurationData.getProperties()) {
                final Object exportValue = getExportValue(property, configurationData);

                final Map<String, List<String>> comments = property.getComments();
                if (!comments.isEmpty()) {
                    configurationData.addComments(comments);
                }

                exportValue(writer, pathTraverser, property.getPath(), exportValue);
            }
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            throw new ConfigMeException("Could not save config to '" + path + "'", e);
        } finally {
            onWriteComplete();
        }
    }

    protected final Path getPath() {
        return path;
    }

    // Scheduled for removal in favor of #getPath
    @Deprecated
    protected final File getFile() {
        return path.toFile();
    }

    /**
     * Exports the given value at the provided path.
     *
     * @param writer        the file writer to write with
     * @param pathTraverser the path traverser (e.g. keeps track of which path elements are new)
     * @param path          the path to export at
     * @param value         the value to export
     * @throws IOException .
     */
    protected void exportValue(Writer writer, PropertyPathTraverser pathTraverser, String path, Object value) throws IOException {
        if (value == null) {
            return;
        }

        if (value instanceof Map<?, ?> && !((Map<?, ?>) value).isEmpty()) {
            final String pathPrefix = path.isEmpty() ? "" : path + ".";
            //noinspection unchecked
            for (Map.Entry<String, ?> entry : ((Map<String, ?>) value).entrySet()) {
                exportValue(writer, pathTraverser, pathPrefix + entry.getKey(), entry.getValue());
            }
        } else {
            List<PathElement> pathElements = pathTraverser.getPathElements(path);

            for (PathElement pathElement : pathElements) {
                writeIndentingBetweenLines(writer, pathElement);
                writeComments(writer, pathElement.getIndentationLevel(), pathElement);
                writer.append(getNewLineIfNotFirstElement(pathElement))
                        .append(indent(pathElement.getIndentationLevel()))
                        .append(escapePathElementIfNeeded(pathElement.getName()))
                        .append(":");
            }

            writer.append(" ")
                    .append(toYamlIndented(value, pathElements.get(pathElements.size() - 1).getIndentationLevel()));
        }
    }

    /**
     * Writes the given comment lines as YAML comments at the given indentation level.
     *
     * @param writer      the writer to write with
     * @param indentation the level at which the comment lines should be indented
     * @param pathElement the path element for which the comments are being generated
     * @throws IOException .
     */
    protected void writeComments(Writer writer, int indentation, PathElement pathElement) throws IOException {
        if (pathElement.getComments().isEmpty()) {
            return;
        }

        String lineStart = pathElement.isFirstElement() ? "" : "\n";
        String commentStart = indent(indentation) + "# ";
        for (final String comment : pathElement.getComments()) {
            writer.append(lineStart);
            lineStart = "\n";
            writeComment(writer, commentStart, comment);
        }
    }

    private void writeDescription(@NotNull final Writer writer, @Nullable final List<String> comments) throws IOException {
        if (comments == null || comments.isEmpty()) return;

        String lineStart = "";
        String commentStart = "# ";
        final Iterator<String> iterator = comments.iterator();
        while (iterator.hasNext()) {
            final String comment = iterator.next();
            writer.append(lineStart);
            lineStart = "\n";
            writeComment(writer, commentStart, comment);
            if (!iterator.hasNext()) writer.append("\n");
        }
    }

    private void writeComment(final Writer writer, final String commentStart, final String comment) throws IOException {
        if (!comment.isEmpty() && !"\n".equals(comment)) {
            writer.append(commentStart)
                    .append(comment);
        }
    }

    private void writeIndentingBetweenLines(Writer writer, PathElement pathElement) throws IOException {
        int numberOfEmptyLines = options.getNumberOfEmptyLinesBefore(pathElement);
        for (int i = 0; i < numberOfEmptyLines; ++i) {
            writer.append("\n");
        }
    }

    private String getNewLineIfNotFirstElement(PathElement pathElement) {
        return pathElement.isFirstElement() && pathElement.getComments().isEmpty() ? "" : "\n";
    }

    /**
     * Returns the value in its YAML representation with an indentation of the given level. Proper indentation
     * should be applied to all lines except for the first one (such that this method's return value can simply
     * be appended to a properly indented property prefix like {@code name:}).
     *
     * @param value  the value to convert to YAML
     * @param indent level of indentation to use
     * @return the value as YAML at the given indentation level
     */
    protected String toYamlIndented(@Nullable Object value, int indent) {
        String representation = toYaml(value);
        String[] lines = representation.split("\\n");
        return String.join("\n" + indent(indent), lines);
    }

    /**
     * Returns the YAML representation for the given value (belonging to the given value).
     * This method returns the YAML representation of the value only (does not include the key)
     * with no indentation (will be applied afterwards with the appropriate level).
     *
     * @param value the value to transform as YAML
     * @return the YAML representation of the value
     */
    protected String toYaml(@Nullable Object value) {
        if (value instanceof String) {
            return '"' + value.toString() + '"';
        } else if (value instanceof Collection<?>) {
            List<?> list = collectionToList((Collection<?>) value);
            if (list.isEmpty()) return "[]";
            if (list.get(0) instanceof String) return toYamlStringList(list);
            return "\n" + getYamlObject().dump(list);
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            return array.length == 0 ? "[]" : "\n" + getYamlObject().dump(array);
        }
        return getYamlObject().dump(value);
    }

    /**
     * Returns a list of strings with `"` wrapped around it
     *
     * @param list The list to transform
     * @return The transformed list
     */
    private String toYamlStringList(@NotNull final List<?> list) {
        final StringBuilder builder = new StringBuilder();
        builder.append('\n');
        for (final Object obj : list) {
            builder.append("- \"").append(obj.toString()).append('"').append('\n');
        }
        return builder.toString();
    }

    /**
     * Returns a String of whitespace for indentation in YAML at the given level.
     *
     * @param level the desired level of indentation
     * @return whitespace to prepend to a line for proper indentation
     */
    protected String indent(int level) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < level; i++) {
            result.append(indentationSpace);
        }
        return result.toString();
    }

    protected String escapePathElementIfNeeded(String path) {
        return getYamlObject().dump(path).trim();
    }

    /**
     * Called at the end of {@link #exportProperties}, regardless whether the execution was successful or not.
     */
    protected void onWriteComplete() {
        yamlObject = null;
    }

    /**
     * Returns the YAML instance with which values are converted to YAML.
     *
     * @return the YAML instance to use
     */
    protected Yaml getYamlObject() {
        if (yamlObject == null) {
            yamlObject = createNewYaml();
        }
        return yamlObject;
    }

    protected Yaml createNewYaml() {
        DumperOptions options = new DumperOptions();

        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setAllowUnicode(true);
        return new Yaml(options);
    }

    protected final YamlFileResourceOptions getOptions() {
        return options;
    }

    private <T> Object getExportValue(Property<T> property, ConfigurationData configurationData) {
        return property.toExportValue(configurationData.getValue(property));
    }

    private static List<?> collectionToList(Collection<?> collection) {
        return collection instanceof List<?>
                ? (List<?>) collection
                : new ArrayList<>(collection);
    }
}
