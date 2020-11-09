package me.mattstudios.config.configurationdata.samples.inheritance;

import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.PropertyInitializer;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

/**
 * Sample settings holder class with inheritance (don't do this).
 */
public class ChildInheritanceSettingsHolder extends MiddleInheritanceSettingsHolder {

    public static final Property<Double> CHILD_DOUBLE = PropertyInitializer.newProperty("child.double", 5.3);

    public static final Property<String> SAMPLE_SUBTITLE = PropertyInitializer.newProperty("sample.subtitle", "");

}
