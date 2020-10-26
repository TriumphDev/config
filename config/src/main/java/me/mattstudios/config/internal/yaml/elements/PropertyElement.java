package me.mattstudios.config.internal.yaml.elements;

import java.util.Collections;

public final class PropertyElement implements Element {

    private final int indentationSize;
    private final String key;
    private final Object value;

    public PropertyElement(final int indentationSize, final String key, final Object value) {
        this.indentationSize = indentationSize;
        this.key = key;
        this.value = value;
    }

    @Override
    public String getValue() {
        return String.join("", Collections.nCopies(indentationSize, " ")) + key + ": " + value;
    }

}
