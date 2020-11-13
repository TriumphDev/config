package me.mattstudios.config;

import me.mattstudios.config.beanmapper.PropertyMapper;
import me.mattstudios.config.beanmapper.PropertyMapperData;
import me.mattstudios.config.configurationdata.ConfigurationData;
import me.mattstudios.config.configurationdata.ConfigurationDataBuilder;
import me.mattstudios.config.migration.MigrationService;
import me.mattstudios.config.migration.PlainMigrationService;
import me.mattstudios.config.resource.PropertyResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Creates {@link SettingsManager} instances.
 */
public final class SettingsManagerBuilder {

    @NotNull
    private final PropertyResource resource;
    private ConfigurationData configurationData;
    private MigrationService migrationService = new PlainMigrationService();
    @Nullable
    private PropertyMapper propertyMapper;

    SettingsManagerBuilder(@NotNull final PropertyResource resource) {
        this.resource = resource;
    }

    /**
     * Sets up configuration data with the input of the given settings holder classes.
     *
     * @param classes the settings holder classes
     * @return this builder
     */
    @NotNull
    @SafeVarargs
    public final SettingsManagerBuilder configurationData(@NotNull final Class<? extends SettingsHolder>... classes) {
        this.configurationData = ConfigurationDataBuilder.createConfiguration(classes);
        return this;
    }

    /**
     * Sets the provided configuration data to the builder.
     *
     * @param configurationData the configuration data
     * @return this builder
     */
    @NotNull
    public SettingsManagerBuilder configurationData(@NotNull final ConfigurationData configurationData) {
        this.configurationData = configurationData;
        return this;
    }

    @NotNull
    public SettingsManagerBuilder propertyMapper(@NotNull final PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
        return this;
    }

    /**
     * Sets the given migration service to the builder.
     *
     * @param migrationService the migration service to use (or null)
     * @return this builder
     */
    @NotNull
    public SettingsManagerBuilder migrationService(@Nullable final MigrationService migrationService) {
        this.migrationService = migrationService;
        return this;
    }

    /**
     * Creates a settings manager instance. It is mandatory that resource and configuration data have been
     * configured beforehand.
     *
     * @return the settings manager
     */
    @NotNull
    public SettingsManager create() {
        Objects.requireNonNull(resource, "resource");
        Objects.requireNonNull(configurationData, "configurationData");
        return new SettingsManagerImpl(resource, configurationData, migrationService, new PropertyMapperData(propertyMapper));
    }
}
