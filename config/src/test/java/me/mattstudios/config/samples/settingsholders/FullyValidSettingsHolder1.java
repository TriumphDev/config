package me.mattstudios.config.samples.settingsholders;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.beanmapper.worldgroup.GameMode;
import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public final class FullyValidSettingsHolder1 implements SettingsHolder {

    @Comment("Int property")
    public static final Property<Integer> INT_PROPERTY =
        newProperty("test.foo", 3);

    @Comment("String prop")
    public static final Property<String> STRING_PROPERTY =
        newProperty("test.str", "default");

    @Comment("Enum property. Possible values: CREATIVE, SURVIVAL")
    public static final Property<GameMode> GAME_MODE_PROPERTY =
        newProperty(GameMode.class, "test.gamemode", GameMode.SURVIVAL);

    private FullyValidSettingsHolder1() {
    }

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("test", "Comment for test section", "Test section comment pt. 2");
    }
}
