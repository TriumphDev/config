package me.mattstudios.config.internal;

import me.mattstudios.config.Config;
import me.mattstudios.config.ConfigHolder;
import me.mattstudios.config.internal.yaml.YamlManager;
import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ConfigManager implements Config {
    @NotNull
    private final Map<Property<?>, Object> properties = new LinkedHashMap<>();
    @NotNull
    private final Map<Property<?>, List<String>> comments = new LinkedHashMap<>();

    @NotNull
    private final YamlManager yamlManager;
    @NotNull
    private final Class<? extends ConfigHolder> holder;

    public ConfigManager(@NotNull final File file, @NotNull final Class<? extends ConfigHolder> holder) {
        this.holder = holder;
        this.yamlManager = new YamlManager(file);

        setupConfig();
    }

    @NotNull
    @Override
    public <T> T getProperty(@NotNull final Property<T> property) {
        return null;
    }

    @Override
    public <T> void setProperty(@NotNull final Property<T> property, @NotNull final T value) {

    }

    public void reload() {

    }

    public void save() {

    }

    private void setupConfig() {
        try {
            for (final Field field : holder.getDeclaredFields()) {
                if (!Property.class.isAssignableFrom(field.getType()) || !Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                System.out.println(field.getName());
                properties.put((Property<?>) field.get(null), "");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(properties);

    }

    public void add(Property<?> property) {
        String[] paths = property.getPath().split("\\.");

    }

}
