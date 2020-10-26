package me.mattstudios.config.properties;

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

    public BaseProperty(@NotNull final T defaultValue) {
        this.defaultValue = defaultValue;
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
    public abstract T determineValue(@NotNull final YamlManager yamlManager);

    public void setComments(@Nullable final List<String> comments) {
        this.comments = comments;
    }

    @Nullable
    @Override
    public List<String> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Property{" +
                "path='" + path + '\'' +
                '}';
    }
}
