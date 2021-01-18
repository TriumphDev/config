package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.PropertyType;
import me.mattstudios.config.resource.PropertyReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Property implementation which relies on a {@link PropertyType}.
 *
 * @param <T> type of property value
 */
public class TypeBasedSupplierProperty<T> extends SupplierBaseProperty<T> {

    private final PropertyType<T> type;

    /**
     * Constructor.
     *
     * @param defaultValue the default value of the property
     * @param type the property type
     */
    public TypeBasedSupplierProperty(@NotNull final Supplier<T> defaultValue, @NotNull final PropertyType<T> type) {
        super(defaultValue);
        this.type = type;
    }

    @Nullable
    @Override
    protected T getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder) {
        return type.convert(reader.getObject(getPath()), errorRecorder, this);
    }

    @Nullable
    @Override
    public Object toExportValue(T value) {
        return type.toExportValue(value, this);
    }
}
