package me.mattstudios.config.samples.settingsholders;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.properties.IntegerProperty;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class MissingCommentsHolder implements SettingsHolder {

    public static final Property<String> STR_PROPERTY = newProperty("lorem.ipsum", "d");

    public static final IntegerProperty INT_PROPERTY = new IntegerProperty("lorem.dolor", 3);

    @Comment("A comment")
    public static final Property<Double> DBL_PROPERTY = newProperty("lorem.amet", 3.40);

    private MissingCommentsHolder() {
    }
}
