package me.mattstudios.config;

import me.mattstudios.config.annotations.TargetObject;
import me.mattstudios.config.beanmapper.PropertyMapper;

import java.util.Map;

public final class CustomMapperJava implements PropertyMapper {

    @TargetObject(Child.class)
    public Object test(final Map<String, Object> map) {
        return null;
    }

}
