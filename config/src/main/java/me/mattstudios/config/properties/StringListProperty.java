package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.PrimitivePropertyType;

import java.util.List;

/**
 * String list property. The lists are immutable.
 */
public class StringListProperty extends ListProperty<String> {

    public StringListProperty(String path, List<String> defaultValue) {
        super(PrimitivePropertyType.STRING, defaultValue);
    }

    @Override
    public Object toExportValue(List<String> value) {
        return value;
    }

}
