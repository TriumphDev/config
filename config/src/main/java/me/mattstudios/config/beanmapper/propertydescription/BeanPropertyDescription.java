package me.mattstudios.config.beanmapper.propertydescription;

import me.mattstudios.config.beanmapper.MapperImpl;
import me.mattstudios.config.utils.TypeInformation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a property within a bean class, as used in {@link MapperImpl}.
 * There, for instance, there is a {@link BeanDescriptionFactory} field responsible for creating bean descriptions.
 * <p>
 * Default implementation is {@link BeanPropertyDescriptionImpl}.
 */
public interface BeanPropertyDescription {

    /**
     * @return the name of the property in the configuration file
     */
    String getName();

    /**
     * @return property type
     */
    TypeInformation getTypeInformation();

    /**
     * Sets the given value on the provided bean for this property. The value should correspond
     * to the {@link #getTypeInformation() property type}.
     *
     * @param bean  the bean to set the property on
     * @param value the value to set
     */
    void setValue(Object bean, Object value);

    /**
     * Returns the value of the property for the given bean.
     *
     * @param bean the bean to read the property from
     * @return the value of the property (can be null)
     */
    @Nullable
    Object getValue(Object bean);

    @NotNull
    List<String> getComments();

}
