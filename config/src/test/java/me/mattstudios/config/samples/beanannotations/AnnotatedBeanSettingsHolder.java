package me.mattstudios.config.samples.beanannotations;

import me.mattstudios.config.annotations.Comment;
import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.properties.Property;

import static me.mattstudios.config.properties.PropertyInitializer.newBeanProperty;

/**
 * SettingsHolder class for {@link AnnotatedBean} setting.
 */
public final class AnnotatedBeanSettingsHolder implements SettingsHolder {

    @Comment("Example: bean with annotated properties")
    public static final Property<AnnotatedBean> ANNOTATED_BEAN =
        newBeanProperty(AnnotatedBean.class, "", new AnnotatedBean());

    private AnnotatedBeanSettingsHolder() {
    }

}
