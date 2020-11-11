package me.mattstudios.config.properties.types;

import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;

public class EnumPropertyType<E extends Enum<E>> implements PropertyType<E> {

    private Class<E> enumType;

    public EnumPropertyType(Class<E> enumType) {
        this.enumType = enumType;
    }

    public static <E extends Enum<E>> EnumPropertyType<E> of(Class<E> type) {
        return new EnumPropertyType<>(type);
    }

    @Override
    public E convert(Object object, ConvertErrorRecorder errorRecorder, @NotNull final Property<?> parentProperty) {
        // If object is enum, then return this object, casting to E
        if (enumType.isInstance(object)) {
            return (E) object;
        }

        // If object is not string, then we return null, because else we cant find an enum
        if (!(object instanceof String)) {
            return null;
        }

        String name = (String) object;
        for (E entry : enumType.getEnumConstants()) {
            if (entry.name().equalsIgnoreCase(name)) {
                return entry;
            }
        }

        return null;
    }

    @Override
    public Object toExportValue(final E value, @NotNull final Property<?> parentProperty) {
        return value.name();
    }

}
