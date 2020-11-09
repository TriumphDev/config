package me.mattstudios.config.samples.beanannotations;

import me.mattstudios.config.annotations.Name;

/**
 * Sample bean with two properties declared with the same name.
 */
public class BeanWithNameClash {

    private String location;
    private String otherProperty;
    private int threshold;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Name("threshold")
    public String getOtherProperty() {
        return otherProperty;
    }

    public void setOtherProperty(String otherProperty) {
        this.otherProperty = otherProperty;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
