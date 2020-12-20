package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.PropertyType;
import me.mattstudios.config.resource.PropertyReader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Property for an immutable map whose keys is of type String and whose values can be configured.
 * The map retains the order of the elements.
 *
 * @param <V> the value type of the map
 */
public class MapProperty<V> extends BaseProperty<Map<String, V>> {

    private final PropertyType<V> type;

    /**
     * Constructor.
     *
     * @param defaultValue the default value of the property
     * @param type the property type of the values
     */
    public MapProperty(PropertyType<V> type, Map<String, V> defaultValue) {
        super(Collections.unmodifiableMap(defaultValue));
        Objects.requireNonNull(type, "type");
        this.type = type;
    }

    @Override
    protected Map<String, V> getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder) {
        Object rawObject = reader.getObject(getPath());

        if (!(rawObject instanceof Map<?, ?>)) {
            return null;
        }

        Map<?, ?> rawMap = (Map<?, ?>) rawObject;
        Map<String, V> map = new LinkedHashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            String path = entry.getKey().toString();
            V value = type.convert(entry.getValue(), errorRecorder, this);

            if (value != null) {
                map.put(path, value);
            }
        }

        return postProcessMap(map);
    }

    @Override
    public Object toExportValue(final Map<String, V> value) {
        Map<String, Object> exportMap = new LinkedHashMap<>();

        for (Map.Entry<String, V> entry : value.entrySet()) {
            exportMap.put(entry.getKey(), type.toExportValue(entry.getValue(), this));
        }

        return exportMap;
    }

    /* Allows to modify the map once its fully built based on the values in the property reader. */
    protected Map<String, V> postProcessMap(Map<String, V> constructedMap) {
        return Collections.unmodifiableMap(constructedMap);
    }
}
