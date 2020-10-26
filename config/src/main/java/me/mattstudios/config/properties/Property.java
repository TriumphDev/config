package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
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

}
