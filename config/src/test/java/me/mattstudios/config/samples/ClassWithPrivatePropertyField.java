package me.mattstudios.config.samples;

import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class ClassWithPrivatePropertyField implements SettingsHolder {

    private static final Property<Integer> PRIVATE_INT_PROPERTY = newProperty("int", 4);
}
