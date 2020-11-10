package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.PropertyType;
import me.mattstudios.config.resource.PropertyReader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * List property of a configurable type. The lists are immutable.
 *
 * @param <T> the property type
 */
public class ListProperty<T> extends BaseProperty<List<T>> {

    private final PropertyType<T> type;

    /**
     * Constructor.
     *
     * @param type the property type
     * @param defaultValue the default value of the property
     */
    public ListProperty(@NotNull final  PropertyType<T> type, List<T> defaultValue) {
        super(Collections.unmodifiableList(defaultValue));
        this.type = type;
    }

    @Nullable
    @Override
    protected List<T> getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder) {
        final List<?> list = reader.getList(getPath());

        if (list != null) {
            return list.stream()
                    .map(elem -> type.convert(elem, errorRecorder))
                    .filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
        }

        return null;
    }

    @Override
    public Object toExportValue(List<T> value) {
        return value.stream()
            .map(type::toExportValue)
            .collect(Collectors.toList());
    }
}
