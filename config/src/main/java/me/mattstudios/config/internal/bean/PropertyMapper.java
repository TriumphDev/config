package me.mattstudios.config.internal.bean;

import org.jetbrains.annotations.NotNull;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PropertyMapper {

    private final Object bean;

    public PropertyMapper(@NotNull final Object bean) {
        this.bean = bean;

        System.out.println(getWritableProperties(bean.getClass()));
    }

    protected List<PropertyDescriptor> getWritableProperties(Class<?> clazz) {
        final PropertyDescriptor[] descriptors;
        try {
            descriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }

        final List<PropertyDescriptor> writableProperties = new ArrayList<>(descriptors.length);
        for (final PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null) {
                writableProperties.add(descriptor);
            }
        }

        return sortPropertiesList(clazz, writableProperties);
    }

    protected List<PropertyDescriptor> sortPropertiesList(Class<?> clazz, List<PropertyDescriptor> properties) {
        Map<String, Integer> fieldNameByIndex = createFieldNameOrderMap(clazz);
        int maxIndex = fieldNameByIndex.size();

        properties.sort(Comparator.comparing(property -> {
            Integer index = fieldNameByIndex.get(property.getName());
            return index == null ? maxIndex : index;
        }));

        return properties;
    }


    protected Map<String, Integer> createFieldNameOrderMap(Class<?> clazz) {
        Map<String, Integer> nameByIndex = new HashMap<>();
        int i = 0;
        for (final Class<?> currentClass : collectClassAndAllParents(clazz)) {
            for (Field field : currentClass.getDeclaredFields()) {
                nameByIndex.put(field.getName(), i);
                ++i;
            }
        }

        return nameByIndex;
    }


    protected List<Class<?>> collectClassAndAllParents(Class<?> clazz) {
        List<Class<?>> parents = new ArrayList<>();

        Class<?> curClass = clazz;
        while (curClass != null && curClass != Object.class) {
            parents.add(curClass);
            curClass = curClass.getSuperclass();
        }

        Collections.reverse(parents);
        return parents;
    }

}
