package me.mattstudios.config.resource.rootcommentsamples;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public final class TestConfig implements SettingsHolder {

    @Comment("Integer property")
    public static final Property<Integer> INT_PROPERTY = newProperty("some.test", 4);

    public static final Property<String> STRING_PROPERTY = newProperty("some.other.property", "hello");

    private TestConfig() {
    }

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("", "Root comment");
        conf.setComment("some", "'some' Section", "Explanation for 'some'");
        conf.setComment("some.other", "Other header");
    }
}
