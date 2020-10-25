package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public interface Property<T> {

    @NotNull
    String getPath();

    @NotNull
    T getDefaultValue();

    @NotNull
    T determineValue(@NotNull final YamlManager yamlManager);

}
