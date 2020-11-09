package me.mattstudios.config.properties.types;

import me.mattstudios.config.beanmapper.DefaultMapper;
import me.mattstudios.config.beanmapper.Mapper;
import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.utils.TypeInformation;

public class BeanPropertyType<B> implements PropertyType<B> {

    private final TypeInformation beanType;
    private final Mapper mapper;

    public BeanPropertyType(TypeInformation beanType, Mapper mapper) {
        this.beanType = beanType;
        this.mapper = mapper;
    }

    public static <B> BeanPropertyType<B> of(Class<B> type, Mapper mapper) {
        return new BeanPropertyType<>(new TypeInformation(type), mapper);
    }

    public static <B> BeanPropertyType<B> of(Class<B> type) {
        return of(type, DefaultMapper.getInstance());
    }

    @Override
    @SuppressWarnings("unchecked")
    public B convert(Object object, ConvertErrorRecorder errorRecorder) {
        return (B) mapper.convertToBean(object, beanType, errorRecorder);
    }

    @Override
    public Object toExportValue(B value) {
        return mapper.toExportValue(value);
    }
}
