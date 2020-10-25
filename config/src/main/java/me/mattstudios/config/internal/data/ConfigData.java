package me.mattstudios.config.internal.data;

import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ConfigData {

    @NotNull
    private final Map<Property<?>, Object> properties = new LinkedHashMap<>();
    @NotNull
    private final Map<Property<?>, List<String>> comments = new LinkedHashMap<>();

    public Map<Property<?>, Object> getProperties() {
        return properties;
    }

}
