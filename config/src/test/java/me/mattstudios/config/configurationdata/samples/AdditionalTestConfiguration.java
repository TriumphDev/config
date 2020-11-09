package me.mattstudios.config.configurationdata.samples;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.PropertyInitializer;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

/**
 * Sample properties class with some additional properties.
 */
public final class AdditionalTestConfiguration implements SettingsHolder {

    @Comment("Seconds to sleep")
    public static final Property<Integer> SLEEP =
        PropertyInitializer.newProperty("additional.sleep", 10);

    @Comment("Additional name")
    public static final Property<String> NAME =
        PropertyInitializer.newProperty("additional.name", "Supplement");

    @Comment("Show additional things")
    public static final Property<Boolean> SHOW_THINGS =
        PropertyInitializer.newProperty("additional.enable", false);


    // Some additional fields that are either invalid properties or irrelevant
    private Property<String> privateProperty = PropertyInitializer.newProperty("test", "toast");
    public static final boolean BOOL = true;
    public final Property<Integer> nonStaticProperty = PropertyInitializer.newProperty("something", 123);

    private AdditionalTestConfiguration() {
    }

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("additional", "Section comment for 'additional'");
        conf.setComment("bogus", "This section does not exist anywhere");
        conf.setComment("other.section");
    }
}
