package me.mattstudios.config.samples.settingsholders;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class SettingsHolderWithVariousCommentLengths implements SettingsHolder {

    @Comment("Length 20 characters")
    public static final Property<String> WITH_20_LEN = newProperty("comment.20", "zwänzg");

    @Comment("Short")
    public static final Property<String> WITH_5_LEN = newProperty("comment.5", "foif");

    @Comment("The length on the property is exactly 40")
    public static final Property<String> WITH_40_LEN = newProperty("comment.40", "vierzg");

    @Comment("Finally, text of 25 chars")
    public static final Property<String> WITH_25_LEN = newProperty("comment.25", "foifezwänzg");

    private SettingsHolderWithVariousCommentLengths() {
    }

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("comment", "This text has length of thirty");
        conf.setComment("", "root");
    }
}
