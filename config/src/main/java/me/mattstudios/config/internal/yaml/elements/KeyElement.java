package me.mattstudios.config.internal.yaml.elements;

import java.util.Collections;

public final class KeyElement implements Element {

    private final int indentationSize;
    private final String text;

    public KeyElement(final int indentationSize, final String text) {
        this.indentationSize = indentationSize;
        this.text = text;
    }

    @Override
    public String getValue() {
        return String.join("", Collections.nCopies(indentationSize, " ")) + text + ":";
    }

}
