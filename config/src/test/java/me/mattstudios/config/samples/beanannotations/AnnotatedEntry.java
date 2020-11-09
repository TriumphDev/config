package me.mattstudios.config.samples.beanannotations;

import me.mattstudios.config.annotations.Name;

/**
 * "Entry" bean class with annotated properties.
 */
public class AnnotatedEntry {

    private long id;
    private boolean hasId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getHasId() {
        return hasId;
    }

    @Name("has-id")
    public void setHasId(boolean hasId) {
        this.hasId = hasId;
    }
}
