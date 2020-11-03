package me.mattstudios.config.internal.bean.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Exporter {

    @Nullable
    String export(@NotNull final Object value, @NotNull final Class<?> clazz);

}
