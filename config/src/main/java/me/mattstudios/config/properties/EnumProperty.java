package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.EnumPropertyType;
import org.jetbrains.annotations.NotNull;

/**
 * Enum property.
 *
 * @param <E> the enum type
 */
public class EnumProperty<E extends Enum<E>> extends TypeBasedProperty<E> {

    public EnumProperty(@NotNull final Class<E> clazz, @NotNull final E defaultValue) {
        super(defaultValue, EnumPropertyType.of(clazz));
    }

}
