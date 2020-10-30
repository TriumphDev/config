package me.mattstudios.config.properties;

import org.jetbrains.annotations.NotNull;

public final class SimpleProperty<T> extends BaseProperty<T> {

    public SimpleProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        final Class<T> type = getType();
        if (type == null) return "";
        return type.equals(String.class) ? indentation + key + ": \"" + value + '"' : indentation + key + ": " + value;
    }

}
