package me.mattstudios.config.internal.bean.types;

import org.jetbrains.annotations.NotNull;

public final class NormalExporter implements Exporter {

    @NotNull
    @Override
    public String export(final @NotNull Object value, final @NotNull Class<?> clazz) {
        return value.toString();
    }

}
