package me.mattstudios.config.internal.yaml.elements;

import java.util.Collections;

public final class CommentElement implements Element {

    private final int indentationSize;

    public CommentElement(final int indentationSize) {
        this.indentationSize = indentationSize;
    }

    @Override
    public String getValue() {
        return String.join("", Collections.nCopies(indentationSize, " ")) + "# Comment";
    }

}
