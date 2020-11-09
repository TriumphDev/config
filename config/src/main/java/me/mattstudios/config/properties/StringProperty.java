package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.PrimitivePropertyType;

/**
 * String property. This extension exists for convenience and backwards compatibility.
 */
public class StringProperty extends TypeBasedProperty<String> {

    public StringProperty(String path, String defaultValue) {
        super(path, defaultValue, PrimitivePropertyType.STRING);
    }
}
