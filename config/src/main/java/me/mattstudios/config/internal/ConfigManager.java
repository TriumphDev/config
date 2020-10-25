package me.mattstudios.config.internal;

import me.mattstudios.config.Config;
import me.mattstudios.config.ConfigHolder;
import me.mattstudios.config.annotations.Path;
import me.mattstudios.config.internal.data.ConfigData;
import me.mattstudios.config.internal.yaml.YamlManager;
import me.mattstudios.config.properties.BaseProperty;
import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class ConfigManager implements Config {

    @NotNull
    private final ConfigData configData = new ConfigData();

    @NotNull
    private final YamlManager yamlManager;
    @NotNull
    private final Class<? extends ConfigHolder> holder;

    public ConfigManager(@NotNull final File file, @NotNull final Class<? extends ConfigHolder> holder) {
        this.holder = holder;
        this.yamlManager = new YamlManager(file, configData);

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

                if (!field.isAnnotationPresent(Path.class)) {
                    continue;
                }

                field.setAccessible(true);
                final String path = field.getAnnotation(Path.class).value();

                final Property<?> property = (Property<?>) field.get(null);

                if (property instanceof BaseProperty) {
                    ((BaseProperty<?>) property).setPath(path);
                }

                add(property);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("Properties - " + configData.getProperties());

        yamlManager.writeProperties();

    }

    public void add(Property<?> property) {
        final Object value = property.determineValue(yamlManager);
        configData.getProperties().put(property, value);
    }

}
