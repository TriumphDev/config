package me.mattstudios.config;

import me.mattstudios.config.configurationdata.ConfigurationData;
import me.mattstudios.config.migration.MigrationService;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.resource.PropertyResource;
import me.mattstudios.config.resource.YamlFileResource;
import me.mattstudios.config.resource.YamlFileResourceOptions;
import me.mattstudios.config.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

/**
 * Settings manager.
 * <p>
 * The settings manager manages a {@link PropertyResource property resource},
 * {@link ConfigurationData configuration data}, and an optional
 * {@link MigrationService migration service}.
 * <p>
 * The settings manager allows to look up and modify properties. After it is initialized, the settings manager
 * should be the only class from ConfigMe that developers need to interact with.
 *
 * @see <a href="https://github.com/AuthMe/ConfigMe">ConfigMe on Github</a>
 * @see SettingsManagerBuilder
 */
public interface SettingsManager {

    /**
     * Gets the given property from the configuration.
     *
     * @param property The property to retrieve
     * @param <T>      The property's type
     * @return The property's value
     */
    @NotNull <T> T get(@NotNull final Property<T> property);

    /**
     * Sets a new value for the given property.
     *
     * @param property The property to modify
     * @param value    The new value to assign to the property
     * @param <T>      The property's type
     */
    <T> void set(@NotNull final Property<T> property, @NotNull final T value);

    /**
     * Reloads the configuration.
     */
    void reload();

    /**
     * Saves the properties to the configuration file.
     */
    void save();

    /**
     * Creates a builder, using the given YAML file to use as property resource.
     *
     * @param file the yaml file to use
     * @return settings manager builder
     */
    @NotNull
    static SettingsManagerBuilder from(@NotNull final Path file) {
        return from(file, YamlFileResourceOptions.builder().build());
    }

    /**
     * Creates a builder, using the given YAML file to use as property resource.
     *
     * @param file the yaml file to use
     * @return settings manager builder
     */
    @NotNull
    static SettingsManagerBuilder from(@NotNull final File file) {
        return from(file.toPath());
    }

    /**
     * Creates a builder, using the given YAML file to use as property resource with the given options.
     *
     * @param path            the yaml file to use
     * @param resourceOptions the resource options
     * @return settings manager builder
     */
    @NotNull
    static SettingsManagerBuilder from(@NotNull final Path path, @NotNull final YamlFileResourceOptions resourceOptions) {
        Utils.createFileIfNotExists(path);
        return new SettingsManagerBuilder(new YamlFileResource(path, resourceOptions));
    }

    /**
     * Creates a builder, using the given YAML file to use as property resource with the given options.
     *
     * @param file            the yaml file to use
     * @param resourceOptions the resource options
     * @return settings manager builder
     */
    @NotNull
    static SettingsManagerBuilder from(@NotNull final File file, @NotNull final YamlFileResourceOptions resourceOptions) {
        return from(file.toPath(), resourceOptions);
    }

    /**
     * Creates a new builder with the given property resource.
     *
     * @param resource the resource to use
     * @return settings manager builder
     */
    @NotNull
    static SettingsManagerBuilder from(@NotNull final PropertyResource resource) {
        return new SettingsManagerBuilder(resource);
    }

}
