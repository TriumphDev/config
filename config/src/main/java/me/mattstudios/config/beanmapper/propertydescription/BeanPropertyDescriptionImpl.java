package me.mattstudios.config.beanmapper.propertydescription;

import me.mattstudios.config.beanmapper.ConfigMeMapperException;
import me.mattstudios.config.utils.TypeInformation;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link BeanPropertyDescription}.
 */
public class BeanPropertyDescriptionImpl implements BeanPropertyDescription {

    @NotNull
    private final String name;
    @NotNull
    private final TypeInformation typeInformation;
    @NotNull
    private final Method getter;
    @NotNull
    private final Method setter;
    @NotNull
    private final List<String> comments;

    /**
     * Constructor.
     *
     * @param name            name of the property in the export
     * @param typeInformation type of the property
     * @param getter          getter for the property
     * @param setter          setter for the property
     */
    public BeanPropertyDescriptionImpl(
            @NotNull final String name,
            @NotNull final TypeInformation typeInformation,
            @NotNull final Method getter,
            @NotNull final Method setter,
            @NotNull final List<String> comments) {
        this.name = name;
        this.typeInformation = typeInformation;
        this.getter = getter;
        this.setter = setter;
        this.comments = comments;
    }

    @NotNull
    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public TypeInformation getTypeInformation() {
        return typeInformation;
    }

    /**
     * Returns the value of the property for the given bean.
     *
     * @param bean the bean to read the property from
     * @return bean value
     */
    @Nullable
    public Object getValue(Object bean) {
        try {
            return getter.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConfigMeMapperException(
                    "Could not get property '" + name + "' from instance '" + bean + "'", e);
        }
    }

    /**
     * Sets the given property to the given value on the provided bean.
     *
     * @param bean  the bean to modify
     * @param value the value to set the property to
     */
    public void setValue(Object bean, Object value) {
        try {
            setter.invoke(bean, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConfigMeMapperException(
                    "Could not set property '" + name + "' to value '" + value + "' on instance '" + bean + "'", e);
        }
    }

    @Override
    public String toString() {
        return "Bean property '" + name + "' with getter '" + getter + "'";
    }
}
