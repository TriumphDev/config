package me.mattstudios.config.samples.settingsholders;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.properties.ListProperty;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.StringListProperty;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public final class FullyValidSettingsHolder2 implements SettingsHolder {

    @Comment({"Double prop", "Double propppp"})
    public static final Property<Double> DOUBLE_PROPERTY =
        newProperty("all.double", 3.0);

    @Comment("The list goes skrrrah")
    public static final ListProperty<String> LIST_PROPERTY =
        new StringListProperty("all.list", "The", "default", "elements");

    private FullyValidSettingsHolder2() {
    }
}
