package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public abstract class BaseProperty<T> implements Property<T> {

    @NotNull
    private String path = "";

    @NotNull
    private final T defaultValue;

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

    @Override
    public String toString() {
        return "Property{" +
                "path='" + path + '\'' +
                '}';
    }
}
