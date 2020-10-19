package me.mattstudios.config;

import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface Config {

    @NotNull <T> T getProperty(@NotNull final Property<T> property);

    <T> void setProperty(@NotNull final Property<T> property, @NotNull final T value);

    void reload();

    void save();

    static ConfigBuilder from(@NotNull final File file) {
        return new ConfigBuilder(file);
    }

}
