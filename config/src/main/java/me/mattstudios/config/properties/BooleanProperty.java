package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.PrimitivePropertyType;

/**
 * Boolean property. This extension exists for convenience and backwards compatibility.
 */
public class BooleanProperty extends TypeBasedProperty<Boolean> {

    public BooleanProperty(String path, Boolean defaultValue) {
        super(path, defaultValue, PrimitivePropertyType.BOOLEAN);
    }
}
