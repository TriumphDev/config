package me.mattstudios.config.samples.settingsholders;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.beanmapper.worldgroup.GameMode;
import me.mattstudios.config.properties.Property;

import java.util.concurrent.TimeUnit;

import static me.mattstudios.config.properties.PropertyInitializer.newProperty;

public class SettingsHolderWithEnumPropertyComments implements SettingsHolder {

    @Comment("Time unit (SECONDS, MINUTES, HOURS)")
    public static final Property<TimeUnit> TIME_UNIT_PROP = newProperty(TimeUnit.class, "sample.timeUnit", TimeUnit.SECONDS);

    public static final Property<GameMode> GAME_MODE = newProperty(GameMode.class, "sample.gameMode", GameMode.SURVIVAL);

}
