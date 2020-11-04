package me.mattstudios.config.internal.yaml;

import org.jetbrains.annotations.NotNull;

public final class Indentation {

    @NotNull
    private final String indentation;
    private final int indentationLevel;

    public Indentation(@NotNull final String indentation, final int indentationLevel) {
        this.indentation = indentation;
        this.indentationLevel = indentationLevel;
    }

    public int getIndentationLevel() {
        return indentationLevel;
    }

    public String getCurrentIndentation() {
        return getLevelIndentation(indentationLevel);
    }

    public String getLevelIndentation(final int indentationLevel) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++) {
            builder.append(indentation);
        }
        return builder.toString();
    }

}
