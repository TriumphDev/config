package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.PropertyType;
import me.mattstudios.config.resource.PropertyReader;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Property implementation which relies on a {@link PropertyType}.
 *
 * @param <T> type of property value
 */
public class TypeBasedProperty<T> extends BaseProperty<T> {

    private final PropertyType<T> type;

    /**
     * Constructor.
     *
     * @param path the path of the property
     * @param defaultValue the default value of the property
     * @param type the property type
     */
    public TypeBasedProperty(String path, T defaultValue, PropertyType<T> type) {
        super(path, defaultValue);
        Objects.requireNonNull(type, "type");
        this.type = type;
    }

    @Nullable
    @Override
    protected T getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder) {
        return type.convert(reader.getObject(getPath()), errorRecorder);
    }

    @Nullable
    @Override
    public Object toExportValue(T value) {
        return type.toExportValue(value);
    }
}
