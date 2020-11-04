package me.mattstudios.config.internal.yaml.elements;

import me.mattstudios.config.internal.yaml.Indentation;
import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

public final class PropertyElement extends BaseElement {

    @NotNull
    private final String key;
    @NotNull
    private final Property<?> property;
    @NotNull
    final Object value;

    public PropertyElement(final int indentationLevel, @NotNull final String key, @NotNull final Property<?> property, @NotNull final Object value) {
        super(indentationLevel);
        this.key = key;
        this.property = property;
        this.value = value;
    }

    @Override
    public @NotNull String getValue(@NotNull final String indentation) {
        return property.getExportValue(key, value, new Indentation(indentation, getIndentationLevel()));
    }

    @Override
    public String toString() {
        return "PropertyElement{" +
                "key='" + key + '\'' +
                ", property=" + property +
                ", value=" + value +
                ", indentation=" + getIndentationLevel() + "}";
    }
}
