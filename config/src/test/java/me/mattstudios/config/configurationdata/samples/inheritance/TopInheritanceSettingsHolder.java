package me.mattstudios.config.configurationdata.samples.inheritance;

import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.PropertyInitializer;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class TopInheritanceSettingsHolder implements SettingsHolder {

    public static final Property<String> STRING_FROM_TOP = PropertyInitializer.newProperty("top.string", "aaa");

}
