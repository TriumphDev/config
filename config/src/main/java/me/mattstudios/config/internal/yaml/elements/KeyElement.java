package me.mattstudios.config.internal.yaml.elements;

public final class KeyElement extends BaseElement {

    private final String key;

    public KeyElement(final int indentationLevel, final String key) {
        super(indentationLevel);
        this.key = key;
    }

    @Override
    public String getValue() {
        return key + ":";
    }

}
