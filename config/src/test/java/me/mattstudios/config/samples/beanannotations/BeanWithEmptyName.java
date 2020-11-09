package me.mattstudios.config.samples.beanannotations;

import me.mattstudios.config.annotations.Name;

/**
 * Sample bean where an export name is null.
 */
public class BeanWithEmptyName {

    private String author;
    private String title;
    private int edition;

    public String getAuthor() {
        return author;
    }

    @Name(value = "")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }
}
