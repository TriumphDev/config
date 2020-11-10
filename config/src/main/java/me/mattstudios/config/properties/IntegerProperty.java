package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.PrimitivePropertyType;

/**
 * Integer property. This extension exists for convenience and backwards compatibility.
 */
public class IntegerProperty extends TypeBasedProperty<Integer> {

    public IntegerProperty(String path, Integer defaultValue) {
        super(defaultValue, PrimitivePropertyType.INTEGER);
    }
}
