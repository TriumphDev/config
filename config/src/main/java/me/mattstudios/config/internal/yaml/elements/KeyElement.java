package me.mattstudios.config.internal.yaml.elements;

import org.jetbrains.annotations.NotNull;

public final class KeyElement extends BaseElement {

    private final String key;

    public KeyElement(final int indentationLevel, final String key) {
        super(indentationLevel);
        this.key = key;
    }

    @NotNull
    @Override
    public String getValue(@NotNull final String indentation) {
        return indentation + key + ":";
    }

}
