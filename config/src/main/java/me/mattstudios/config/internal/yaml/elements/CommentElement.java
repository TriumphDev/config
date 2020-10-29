package me.mattstudios.config.internal.yaml.elements;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CommentElement extends BaseElement {

    @NotNull
    private final List<String> comments;

    public CommentElement(final int indentationLevel, @NotNull final List<String> comments) {
        super(indentationLevel);
        this.comments = comments;
    }

    @Override
    public @NotNull String getValue(@NotNull final String indentation) {
         return "";
    }

    @NotNull
    public List<String> getComments() {
        return comments;
    }

}
