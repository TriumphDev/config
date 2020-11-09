package me.mattstudios.config.resource.rootcommentsamples;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.beanmapper.worldgroup.GameMode;
import me.mattstudios.config.beanmapper.worldgroup.Group;
import me.mattstudios.config.properties.BeanProperty;
import me.mattstudios.config.properties.Property;

import java.util.Collections;

public final class GroupPropertyHolder implements SettingsHolder {

    @Comment("Group configuration number")
    public static final Property<Group> GROUP = new BeanProperty<>(Group.class, "", buildDefaultGroup());

    private GroupPropertyHolder() {
    }

    private static Group buildDefaultGroup() {
        Group group = new Group();
        group.setWorlds(Collections.singletonList("world"));
        group.setDefaultGamemode(GameMode.CREATIVE);
        return group;
    }
}
