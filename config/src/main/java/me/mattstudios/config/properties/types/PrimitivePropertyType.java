package me.mattstudios.config.properties.types;

import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Standard implementations of property types for basic types.
 *
 * @param <T> the type the property type produces
 */
public class PrimitivePropertyType<T> implements PropertyType<T> {

    private final Function<Object, T> convertFunction;
    private final Function<T, Object> exportValueFunction;

    /**
     * Constructor.
     *
     * @param convertFunction function to convert to the given type
     */
    public PrimitivePropertyType(Function<Object, T> convertFunction) {
        this(convertFunction, t -> t);
    }

    /**
     * Constructor.
     *
     * @param convertFunction function to convert to the given type
     * @param exportValueFunction function to convert a value to its export value
     */
    public PrimitivePropertyType(Function<Object, T> convertFunction, Function<T, Object> exportValueFunction) {
        this.convertFunction = convertFunction;
        this.exportValueFunction = exportValueFunction;
    }

    @Override
    public T convert(Object object, ConvertErrorRecorder errorRecorder,  @NotNull final Property<?> parentProperty) {
        return convertFunction.apply(object);
    }

    @Override
    public Object toExportValue(T value, @NotNull final Property<?> parentProperty) {
        return exportValueFunction.apply(value);
    }

    /* Helper to create property types which convert from a Number object. */
    static <T> PrimitivePropertyType<T> fromNumber(Function<Number, T> function) {
        return new PrimitivePropertyType<>(object -> object instanceof Number ? function.apply((Number) object) : null);
    }
}
