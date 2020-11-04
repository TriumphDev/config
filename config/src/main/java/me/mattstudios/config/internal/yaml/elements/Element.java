package me.mattstudios.config.internal.yaml.elements;

import org.jetbrains.annotations.NotNull;

public interface Element {

    @NotNull
    String getValue(@NotNull final String indentation);

}
