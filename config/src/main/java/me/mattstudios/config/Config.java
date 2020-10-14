package me.mattstudios.config;

import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Config {

    @NotNull
    <T> T getProperty(@NotNull final Property<T> property);

    @Nullable
    <T> T getNullableProperty(@NotNull final Property<T> property);

    <T> void setProperty(@NotNull final Property<T> property, @NotNull final T value);

    void reload();

    void save();

}
