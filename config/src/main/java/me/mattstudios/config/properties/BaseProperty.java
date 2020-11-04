package me.mattstudios.config.properties;

import me.mattstudios.config.internal.bean.PropertyExporter;
import me.mattstudios.config.internal.yaml.Indentation;
import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseProperty<T> implements Property<T> {

    @NotNull
    private String path = "";
    @NotNull
    private final T defaultValue;
    @Nullable
    private List<String> comments;
    @Nullable
    private final Class<T> type;

    public BaseProperty(@NotNull final T defaultValue, @Nullable final Class<T> type) {
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public BaseProperty(@NotNull final T defaultValue) {
        this(defaultValue, null);
    }

    public void setPath(@NotNull final String path) {
        this.path = path;
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @NotNull
    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @NotNull
    @Override
    public T determineValue(@NotNull final YamlManager yamlManager) {
        final T value = yamlManager.getValue(getPath(), type);
        if (value == null) return getDefaultValue();
        return value;
    }

    @Override
    public @NotNull String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final Indentation indentation) {
        final Class<T> type = getType();
        if (type == null) return "";

        return exportComments(indentation) +
                indentation.getCurrentIndentation() +
                key +
                ": " +
                PropertyExporter.exportValue(value, type, indentation);
    }

    protected String exportComments(@NotNull final Indentation indentation) {
        if (comments == null) return "";

        final StringBuilder builder = new StringBuilder();
        for (final String comment : comments) {
            if (!comment.isEmpty() && !"\n".equals(comment)) {
                builder.append(indentation.getCurrentIndentation()).append("# ");
            }

            builder.append(comment).append("\n");
        }
        return builder.toString();
    }

    public void setComments(@Nullable final List<String> comments) {
        this.comments = comments;
    }

    @Nullable
    protected Class<T> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "path='" + path + '\'' +
                '}';
    }

}
