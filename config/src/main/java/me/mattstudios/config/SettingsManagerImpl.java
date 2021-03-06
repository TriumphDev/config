package me.mattstudios.config;

import me.mattstudios.config.beanmapper.PropertyMapperData;
import me.mattstudios.config.configurationdata.ConfigurationData;
import me.mattstudios.config.migration.MigrationService;
import me.mattstudios.config.properties.BaseProperty;
import me.mattstudios.config.properties.OptionalProperty;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.resource.PropertyReader;
import me.mattstudios.config.resource.PropertyResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Default implementation of {@link SettingsManager}. Use the {@link SettingsManagerBuilder} to create instances.
 * <p>
 * The settings manager unites a {@link PropertyResource property resource},
 * a {@link MigrationService migration service} and the list of known properties
 * (typically gathered from {@link SettingsHolder} classes).
 * <p>
 * The settings manager allows to look up and modify properties. Thus, the settings
 * manager fulfills the most typical operations on a configuration in an application.
 * After initializing the settings manager, it is usually the only class from ConfigMe
 * you interact with.
 *
 * @see PropertyResource
 * @see ConfigurationData
 * @see MigrationService
 */
public class SettingsManagerImpl implements SettingsManager {

    private final ConfigurationData configurationData;
    private final PropertyResource resource;
    private final MigrationService migrationService;
    @NotNull
    private final PropertyMapperData propertyMapper;

    /**
     * Constructor. Use {@link SettingsManagerBuilder} to create instances.
     *
     * @param resource          the property resource to read from and write to
     * @param configurationData the configuration data
     * @param migrationService  migration service to check the property resource with
     */
    protected SettingsManagerImpl(
            @NotNull final PropertyResource resource,
            @NotNull final ConfigurationData configurationData,
            @Nullable final MigrationService migrationService,
            @NotNull final PropertyMapperData propertyMapper
    ) {
        this.configurationData = configurationData;
        this.resource = resource;
        this.migrationService = migrationService;
        this.propertyMapper = propertyMapper;
        loadMapper();
        loadFromResourceAndValidate();
    }

    private void loadMapper() {
        for (final Property<?> property : configurationData.getProperties()) {
            if (property instanceof BaseProperty) {
                final BaseProperty<?> baseProperty = (BaseProperty<?>) property;
                baseProperty.setPropertyMapper(propertyMapper);
            }

            if (property instanceof OptionalProperty) {
                final OptionalProperty<?> optionalProperty = (OptionalProperty<?>) property;
                optionalProperty.setPropertyMapper(propertyMapper);
            }
        }
    }

    /**
     * Gets the given property from the configuration.
     *
     * @param property the property to retrieve
     * @param <T>      the property's type
     * @return the property's value
     */
    @NotNull
    @Override
    public <T> T get(@NotNull final Property<T> property) {
        return configurationData.getValue(property);
    }

    /**
     * Sets a new value for the given property.
     *
     * @param property the property to modify
     * @param value    the new value to assign to the property
     * @param <T>      the property's type
     */
    @Override
    public <T> void set(@NotNull final Property<T> property, @NotNull final T value) {
        configurationData.setValue(property, value);
    }

    @Override
    public void reload() {
        loadFromResourceAndValidate();
    }

    @Override
    public void save() {
        resource.exportProperties(configurationData);
    }

    /**
     * Reads the configuration file and executes the migration service (if present). Saves the file if migrations
     * have been applied.
     */
    protected void loadFromResourceAndValidate() {
        final PropertyReader reader = resource.createReader();
        configurationData.initializeValues(reader);

        if (migrationService != null
                && migrationService.checkAndMigrate(reader, configurationData) == MigrationService.MIGRATION_REQUIRED) {
            save();
        }
    }

    protected final PropertyResource getPropertyResource() {
        return resource;
    }

    protected final ConfigurationData getConfigurationData() {
        return configurationData;
    }

    @Nullable
    protected final MigrationService getMigrationService() {
        return migrationService;
    }
}
