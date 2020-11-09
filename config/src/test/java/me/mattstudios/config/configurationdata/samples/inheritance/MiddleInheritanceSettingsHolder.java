package me.mattstudios.config.configurationdata.samples.inheritance;

import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.PropertyInitializer;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class MiddleInheritanceSettingsHolder extends TopInheritanceSettingsHolder {

    public static final Property<Integer> MIDDLE_VERSION = PropertyInitializer.newProperty("middle.version", 5);

    public static final Property<String> SAMPLE_NAME = PropertyInitializer.newProperty("sample.name", "Sample");

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("middle", "Comes from the holder in the middle");
    }
}
