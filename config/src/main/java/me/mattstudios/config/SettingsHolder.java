package me.mattstudios.config;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.configurationdata.CommentsConfiguration;
import me.mattstudios.config.configurationdata.ConfigurationDataBuilder;
import me.mattstudios.config.properties.Property;

/**
 * Marker interface for classes that define Property objects.
 * <p>
 * Declare your properties as {@code public static final} fields
 * of {@link Property} type in a class
 * which implements this interface.
 * <p>
 * Classes implementing this interface must have a no-args constructor (any visibility).
 *
 * @see Property
 * @see ConfigurationDataBuilder
 */
public interface SettingsHolder {

    /**
     * Allows to register comments for sections and properties by overriding this method and interacting
     * with the given configuration object.
     * <p>
     * Note that comments can also be put on Property fields with the {@link Comment} annotation.
     *
     * @param conf the comments configuration
     */
    default void registerComments(CommentsConfiguration conf) {
        // override to register comments for sections
    }
}
