package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public final class EnumProperty<T extends Enum<T>> extends BaseProperty<Enum<T>> {

    @NotNull
    private final Class<T> type;

    public EnumProperty(@NotNull final Enum<T> defaultValue, @NotNull final Class<T> type) {
        super(defaultValue);
        this.type = type;
    }

    @NotNull
    @Override
    public Enum<T> determineValue(@NotNull final YamlManager yamlManager) {
        final String enumValue = yamlManager.getValue(getPath(), String.class);
        if (enumValue == null) return getDefaultValue();

        Enum<T> value = null;

        for (final Enum<T> enm : type.getEnumConstants()) {
            if (enm.name().equalsIgnoreCase(enumValue)) value = enm;
        }

        if (value == null) return getDefaultValue();

        return value;
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        return indentation + key + ": \"" + value + '"';
    }

}
