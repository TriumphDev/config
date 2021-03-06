package me.mattstudios.config.properties;

import me.mattstudios.config.beanmapper.PropertyMapperData;
import me.mattstudios.config.properties.convertresult.PropertyValue;
import me.mattstudios.config.resource.PropertyReader;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Property which may be empty.
 * <p>
 * Wraps another property with an {@link Optional}: if a property is not present in the property resource,
 * {@link Optional#empty} is returned.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalProperty<T> implements Property<Optional<T>> {

    private final Property<T> baseProperty;
    private final Optional<T> defaultValue;
    @NotNull
    private final Map<String, List<String>> comments = new LinkedHashMap<>();
    @NotNull
    private PropertyMapperData propertyMapper;

    public OptionalProperty(Property<T> baseProperty, T defaultValue) {
        this.baseProperty = baseProperty;
        this.defaultValue = Optional.of(defaultValue);
    }

    public Property<T> getBaseProperty() {
        return baseProperty;
    }

    @Override
    public String getPath() {
        return baseProperty.getPath();
    }

    @Override
    public PropertyValue<Optional<T>> determineValue(PropertyReader reader) {
        PropertyValue<T> basePropertyValue = baseProperty.determineValue(reader);
        Optional<T> value = basePropertyValue.isValidInResource()
                ? Optional.ofNullable(basePropertyValue.getValue())
                : Optional.empty();

        // Propagate the false "valid" property if the reader has a value at the base property's path
        // and the base property says it's invalid -> triggers a rewrite to get rid of the invalid value.
        boolean isWrongInResource = !basePropertyValue.isValidInResource() && reader.contains(baseProperty.getPath());
        return isWrongInResource
                ? PropertyValue.withValueRequiringRewrite(value)
                : PropertyValue.withValidValue(value);
    }

    @Override
    public Optional<T> getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isValidValue(Optional<T> value) {
        if (!value.isPresent()) {
            return false;
        }
        return value.map(baseProperty::isValidValue).orElse(true);
    }

    @Override
    public void addComments(@NotNull final String path, @NotNull final List<String> comments) {
        this.comments.put(path, comments);
    }

    public void setPropertyMapper(@NotNull final PropertyMapperData propertyMapper) {
        this.propertyMapper = propertyMapper;
        ((BaseProperty<?>) baseProperty).setPropertyMapper(propertyMapper);
    }

    @NotNull
    @Override
    public PropertyMapperData getPropertyMapper() {
        return propertyMapper;
    }

    @NotNull
    @Override
    public Map<String, List<String>> getComments() {
        return Collections.unmodifiableMap(comments);
    }

    @Override
    public Object toExportValue(Optional<T> value) {
        return value.map(baseProperty::toExportValue).orElse(null);
    }

}
