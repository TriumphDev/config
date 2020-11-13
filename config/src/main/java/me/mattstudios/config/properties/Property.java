package me.mattstudios.config.properties;

import me.mattstudios.config.beanmapper.PropertyMapperData;
import me.mattstudios.config.configurationdata.ConfigurationDataImpl;
import me.mattstudios.config.migration.MigrationService;
import me.mattstudios.config.properties.convertresult.PropertyValue;
import me.mattstudios.config.properties.types.EnumPropertyType;
import me.mattstudios.config.properties.types.PrimitivePropertyType;
import me.mattstudios.config.resource.PropertyReader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Property interface. A property knows its path, its type, and can convert the values from
 * a property reader to a value of its type (if the values of the reader are valid).
 * Properties define their path and their behavior but do not keep track of their value.
 * <p>
 * Property implementations should always extend from {@link BaseProperty} instead of implementing
 * this interface directly.
 *
 * @param <T> the property type
 */
public interface Property<T> {

    /**
     * @return the path of the property
     */
    String getPath();

    /**
     * Returns the value, based on the given reader, which should be used for this property. By default
     * this is the value as constructed from the reader, and otherwise the default value. Implementations
     * of {@link BaseProperty} never return null. The return value must be in sync with
     * {@link #isValidValue(Object)}.
     *
     * @param propertyReader the reader to construct the value from (if possible)
     * @return the value to associate to this property
     */
    PropertyValue<T> determineValue(PropertyReader propertyReader);

    /**
     * Convenience method to check whether the property is present in the given reader and a valid representation
     * of the property type.
     * Prefer {@link #determineValue(PropertyReader)} if you need the value afterwards to avoid performing the same
     * operation twice.
     *
     * @param propertyReader the reader to check the value in
     * @return true if a value is available for the property and it is valid, false otherwise
     */
    default boolean isValidInResource(PropertyReader propertyReader) {
        return determineValue(propertyReader).isValidInResource();
    }

    /**
     * Returns the default value of this property.
     *
     * @return the default value
     */
    T getDefaultValue();

    /**
     * Returns whether the value can be associated to the given property, i.e. whether it fulfills all
     * requirements which may be imposed by the property type.
     * <p>
     * This method is used in {@link ConfigurationDataImpl#setValue}, which
     * throws an exception if this method returns {@code false}. Therefore, this method is intended as a last catch
     * for invalid values and to ensure that programmatically no invalid value can be set. Extended validation of
     * values encountered in the property reader should be preferably handled in {@link #determineValue},
     * or in an extension of {@link MigrationService}.
     *
     * @param value the value to check
     * @return true if the value can be used for the property, false otherwise
     */
    boolean isValidValue(T value);

    /**
     * Converts the given value to a representation that is suitable for exporting by a property resource. The
     * return value should contain the data in the given value represented with only basic types (String, Number,
     * Boolean), as well as lists and maps thereof.
     * <p>
     * The values which are suitable for returning depend on the support of the used property resource. By default,
     * the supported types include {@code null}, String, Integer, Double, Boolean; Collection of the aforementioned
     * types; Map with String keys and values of any of the aforementioned types. Maps and collections can be nested at
     * any arbitrary level. Null signifies that the property/value should be skipped in the export.
     *
     * @param value the value to convert to an export value
     * @return value to use for export, null to skip the property
     */
    @Nullable
    Object toExportValue(T value);

    void addComments(@NotNull final String path, @NotNull final List<String> comments);

    @NotNull
    Map<String, List<String>> getComments();

    @NotNull
    PropertyMapperData getPropertyMapper();

    /**
     * Creates a new boolean property.
     *
     * @param defaultValue the default value
     * @return the created property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<Boolean> create(final boolean defaultValue) {
        return new BooleanProperty("bool", defaultValue);
    }

    /**
     * Creates a new integer property.
     *
     * @param defaultValue the default value
     * @return the created property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<Integer> create(final int defaultValue) {
        return new TypeBasedProperty<>(defaultValue, PrimitivePropertyType.INTEGER);
    }

    /**
     * Creates a new double property.
     *
     * @param defaultValue the default value
     * @return the created property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<Double> create(final double defaultValue) {
        return new TypeBasedProperty<>(defaultValue, PrimitivePropertyType.DOUBLE);
    }

    /**
     * Creates a new String property.
     *
     * @param defaultValue the default value
     * @return the created property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<String> create(@NotNull final String defaultValue) {
        return new TypeBasedProperty<>(defaultValue, PrimitivePropertyType.STRING);
    }

    /**
     * Creates a new enum property.
     *
     * @param defaultValue the default value
     * @param <E>          the enum type
     * @return the created enum property
     */
    @NotNull
    @Contract("_, -> new")
    static <E extends Enum<E>> Property<E> create(@NotNull final E defaultValue) {
        //noinspection unchecked
        return new EnumProperty<>((Class<E>) defaultValue.getClass(), defaultValue);
    }

    /**
     * Creates a new String list property.
     *
     * @param defaultValue the items in the default list
     * @return the created list property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<List<String>> create(@NotNull final List<String> defaultValue) {
        return new ListProperty<>(PrimitivePropertyType.STRING, defaultValue);
    }

    /**
     * Creates a new String Set property.
     *
     * @param defaultValue the items in the default list
     * @return the created list property
     */
    @NotNull
    @Contract("_ -> new")
    static Property<Set<String>> create(@NotNull final Set<String> defaultValue) {
        return new SetProperty<>(PrimitivePropertyType.STRING, defaultValue);
    }

    /**
     * Creates a new bean property.
     *
     * @param defaultValue default value
     * @param <B>          the bean type
     * @return the created bean property
     */
    @NotNull
    @Contract("_ -> new")
    static <B> Property<B> create(@NotNull final B defaultValue) {
        //noinspection unchecked
        return new BeanProperty<>((Class<B>) defaultValue.getClass(), defaultValue);
    }

    // --------------
    // Optional flavors
    // --------------
    static Property<Optional<Boolean>> createOptional(@Nullable final Boolean defaultValue) {
        return new OptionalProperty<>(new TypeBasedProperty<>(false, PrimitivePropertyType.BOOLEAN), defaultValue);
    }

    static Property<Optional<Integer>> createOptional(@Nullable final Integer defaultValue) {
        return new OptionalProperty<>(new TypeBasedProperty<>(0, PrimitivePropertyType.INTEGER), defaultValue);
    }

    static Property<Optional<String>> createOptional(@Nullable final String defaultValue) {
        return new OptionalProperty<>(new TypeBasedProperty<>("", PrimitivePropertyType.STRING), defaultValue);
    }

    static <E extends Enum<E>> Property<Optional<E>> createOptional(@NotNull final E defaultValue) {
        // default value may never be null, so get the first entry in the enum class
        //noinspection unchecked
        return new OptionalProperty<>(new TypeBasedProperty<E>(defaultValue, EnumPropertyType.of(defaultValue.getClass())), defaultValue);
    }

}
