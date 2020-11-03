package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public final class EnumProperty<T extends Enum<T>> extends BaseProperty<T> {

    public EnumProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

    @NotNull
    @Override
    public T determineValue(@NotNull final YamlManager yamlManager) {
        final String enumValue = yamlManager.getValue(getPath(), String.class);
        if (enumValue == null) return getDefaultValue();

        Enum<T> value = null;

        final Class<T> type = getType();
        if (type == null) return getDefaultValue();

        for (final Enum<T> enm : type.getEnumConstants()) {
            if (enm.name().equalsIgnoreCase(enumValue)) value = enm;
        }

        if (value == null) return getDefaultValue();
        return (T) value;
    }

}
