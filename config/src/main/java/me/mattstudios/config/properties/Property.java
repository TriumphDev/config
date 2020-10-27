package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Property<T> {

    @NotNull
    String getPath();

    @NotNull
    T getDefaultValue();

    @NotNull
    T determineValue(@NotNull final YamlManager yamlManager);

    @Nullable
    List<String> getComments();

    @NotNull
    String getExportValue(@NotNull final String key, @NotNull final Object value);

    @NotNull
    @Contract("_ -> new")
    static Property<String> create(@NotNull final String defaultValue) {
        return new StringProperty(defaultValue);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Number> create(final int defaultValue) {
        return new NumberProperty(defaultValue);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Number> create(final double defaultValue) {
        return new NumberProperty(defaultValue);
    }

}
