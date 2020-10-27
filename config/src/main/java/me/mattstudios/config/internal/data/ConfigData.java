package me.mattstudios.config.internal.data;

import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ConfigData {

    @NotNull
    private final Map<Property<?>, Object> properties = new LinkedHashMap<>();
    @NotNull
    private final Map<String, List<String>> pathComments = new LinkedHashMap<>();

    @NotNull
    public Map<Property<?>, Object> getProperties() {
        return properties;
    }

    public void addProperty(@NotNull final Property<?> property, @NotNull final Object value) {
        properties.put(property, value);
    }

    public void addPathComment(@NotNull String path, @NotNull final List<String> comments) {
        pathComments.put(path, comments);
    }

    @NotNull
    public <T> T get(Property<T> property) {
        //noinspection unchecked
        return (T) properties.get(property);
    }

    @Nullable
    public List<String> getComment(@NotNull final String key) {
        return pathComments.get(key);
    }

}
