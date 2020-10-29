package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public final class SimpleProperty<T> extends BaseProperty<T> {

    @NotNull
    private final Class<T> type;

    public SimpleProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue);
        this.type = type;
    }

    @NotNull
    @Override
    public T determineValue(@NotNull final YamlManager yamlManager) {
        final T value = yamlManager.getValue(getPath(), type);
        if (value == null) return getDefaultValue();
        return value;
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        return type.equals(String.class) ? indentation + key + ": \"" + value + '"' : indentation + key + ": " + value;
    }

}
