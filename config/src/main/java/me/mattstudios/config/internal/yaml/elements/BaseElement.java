package me.mattstudios.config.internal.yaml.elements;

public abstract class BaseElement implements Element {

    private final int indentationLevel;

    public BaseElement(final int indentationLevel) {
        this.indentationLevel = indentationLevel;
    }

    @Override
    public int getIndentationLevel() {
        return indentationLevel;
    }

}
