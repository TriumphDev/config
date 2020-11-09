package me.mattstudios.config.properties;

import me.mattstudios.config.beanmapper.DefaultMapper;
import me.mattstudios.config.beanmapper.Mapper;
import me.mattstudios.config.exception.ConfigMeException;
import me.mattstudios.config.properties.types.BeanPropertyType;
import me.mattstudios.config.utils.TypeInformation;

public class BeanProperty<T> extends TypeBasedProperty<T> {

    public BeanProperty(Class<T> beanType, String path, T defaultValue) {
        this(beanType, path, defaultValue, DefaultMapper.getInstance());
    }

    public BeanProperty(Class<T> beanType, String path, T defaultValue, Mapper mapper) {
        super(path, defaultValue, BeanPropertyType.of(beanType, mapper));
    }

    /**
     * Constructor. Allows to instantiate bean properties with generic types. Since it is hard to validate that
     * the default value is actually correct, it is recommended to extend this class with specific type parameters.
     *
     * @param beanType the bean type
     * @param path the path
     * @param defaultValue the default value
     * @param mapper the mapper to map with
     */
    protected BeanProperty(TypeInformation beanType, String path, T defaultValue, Mapper mapper) {
        super(path, defaultValue, new BeanPropertyType<>(beanType, mapper));
        if (!beanType.getSafeToWriteClass().isInstance(defaultValue)) {
            throw new ConfigMeException(
                "Default value for path '" + path + "' does not match bean type '" + beanType + "'");
        }
    }
}
