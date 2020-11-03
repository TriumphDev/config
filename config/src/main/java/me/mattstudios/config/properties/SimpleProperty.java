package me.mattstudios.config.properties;

import org.jetbrains.annotations.NotNull;

public final class SimpleProperty<T> extends BaseProperty<T> {

    public SimpleProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

}
