package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.convertresult.PropertyValue;
import me.mattstudios.config.resource.PropertyReader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Base implementation of {@link Property}. All properties should extend from this class.
 * <p>
 * This base implementation makes interacting with properties null safe by guaranteeing that the default value
 * and its {@link #determineValue determined value} can never be null.
 *
 * @param <T> the property type
 */
public abstract class BaseProperty<T> implements Property<T> {

    @NotNull
    private String path = "";
    @NotNull
    private final T defaultValue;

    /**
     * Constructor.
     *
     * @param defaultValue the default value of the property
     */
    public BaseProperty(@NotNull final T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setPath(@NotNull final String path) {
        this.path = path;
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public PropertyValue<T> determineValue(PropertyReader reader) {
        ConvertErrorRecorder errorRecorder = new ConvertErrorRecorder();
        T value = getFromReader(reader, errorRecorder);
        if (isValidValue(value)) {
            return new PropertyValue<>(value, errorRecorder.isFullyValid());
        }
        return PropertyValue.withValueRequiringRewrite(getDefaultValue());
    }

    @Override
    public boolean isValidValue(T value) {
        return value != null;
    }

    /**
     * Constructs the value of the property from the property reader. Returns null if no value is
     * available in the reader or if it cannot be used to construct a value for this property.
     *
     * @param reader        the reader to read from
     * @param errorRecorder error recorder to register errors even if a valid value is returned
     * @return value based on the reader, or null if not applicable
     */
    @Nullable
    protected abstract T getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder);

    @Override
    public String toString() {
        return "Property '" + path + "'";
    }
}
