package me.mattstudios.config;

import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigManager implements Config {

    private final Map<Property<?>, Object> properties = new LinkedHashMap<>();

    @NotNull
    @Override
    public <T> T getProperty(@NotNull final Property<T> property) {
        final Object value = properties.get(property);
        if (value == null) throw new NullPointerException("TODO");
        //noinspection unchecked
        return (T) value;
    }

    @Nullable
    @Override
    public  <T> T getNullableProperty(@NotNull final Property<T> property) {
        //noinspection unchecked
        return (T) properties.get(property);
    }

    @Override
    public <T> void setProperty(@NotNull final Property<T> property, @NotNull final T value) {

    }

    public void reload() {

    }

    public void save() {

    }
}
