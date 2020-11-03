package me.mattstudios.config.internal.yaml;

import me.mattstudios.config.internal.data.ConfigData;
import me.mattstudios.config.internal.yaml.elements.CommentElement;
import me.mattstudios.config.internal.yaml.elements.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public final class YamlManager {

    @NotNull
    private final File file;
    @NotNull
    private final Yaml yaml;

    @NotNull
    private static final String indentation = "  ";

    @NotNull
    private final YamlReader yamlReader;
    private final ConfigData configData;

    public YamlManager(@NotNull final File file, @NotNull final ConfigData configData) {
        this.file = file;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        yaml = createFile();
        yamlReader = new YamlReader(file);
        this.configData = configData;
    }

    private Yaml createFile() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        return new Yaml(options);
    }

    @Nullable
    public <T> T getValue(@NotNull String path, @Nullable final Class<T> clazz) {
        if (clazz == null) return null;
        return yamlReader.get(path, clazz);
    }

    public void getInt(@NotNull String path) {
        final Integer number = yamlReader.get(path, Integer.class);
    }

    public void getMap(@NotNull String path) {
    }

    public void writeProperties() {
        try (final OutputStream outputStream = Files.newOutputStream(file.toPath());
             final OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

            final PropertyScanner scanner = new PropertyScanner(configData);

            final List<String> description = configData.getComment("FILE_COMMENT");
            if (description != null) {
                for (final String comment : description) {
                    if (!comment.isEmpty() && !"\n".equals(comment)) {
                        writer.append(getCurrentIndentation(0))
                                .append("# ");
                    }

                    writer.append(comment).append("\n");
                }
            }

            for (final Element element : scanner.getElements()) {
                if (element instanceof CommentElement) {
                    final List<String> comments = ((CommentElement) element).getComments();
                    for (final String comment : comments) {
                        if (!comment.isEmpty() && !"\n".equals(comment)) {
                            writer.append(getCurrentIndentation(element.getIndentationLevel()))
                                    .append("# ");
                        }

                        writer.append(comment).append("\n");
                    }

                    continue;
                }

                writer.append(element.getValue(getCurrentIndentation(element.getIndentationLevel())))
                        .append("\n");

            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentIndentation(final int indentationLevel) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++) {
            builder.append(indentation);
        }
        return builder.toString();
    }

}
