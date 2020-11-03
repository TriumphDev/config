package me.mattstudios.config.internal.bean;

import org.jetbrains.annotations.NotNull;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class PropertyMapper {

    private final Object value;
    private final Class<?> clazz;

    public PropertyMapper(@NotNull final Object value) {
        this.value = value;
        this.clazz = value.getClass();

        final List<PropertyDescriptor> properties = getProperties();
        for (final PropertyDescriptor property : properties) {
            System.out.println(property.getName() + " - " + property.getPropertyType());
        }

    }

    protected List<PropertyDescriptor> getProperties() {
        final PropertyDescriptor[] descriptors;
        try {
            descriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }

        final List<PropertyDescriptor> properties = new ArrayList<>(descriptors.length);
        for (final PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getWriteMethod() == null || descriptor.getReadMethod() == null) continue;
            properties.add(descriptor);
        }

        final List<Class<?>> classes = collectClassAndAllParents(clazz);
        final int maxIndex = getFieldsCount(classes);

        properties.sort(Comparator.comparing(property -> {
            Integer index = getFieldIndex(property.getName(), classes);
            return index == null ? maxIndex : index;
        }));

        return properties;
    }


    private int getFieldsCount(@NotNull final List<Class<?>> classes) {
        int fields = 0;
        for (final Class<?> currentClass : classes) {
            fields += currentClass.getDeclaredFields().length;
        }
        return fields;
    }

    private Integer getFieldIndex(@NotNull final String fieldName, @NotNull final List<Class<?>> classes) {
        for (final Class<?> currentClass : classes) {
            final Field[] fields = currentClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().equals(fieldName)) return i;
            }
        }

        return null;
    }

    protected List<Class<?>> collectClassAndAllParents(Class<?> clazz) {
        final List<Class<?>> parents = new ArrayList<>();

        Class<?> curClass = clazz;
        while (curClass != null && curClass != Object.class) {
            parents.add(curClass);
            curClass = curClass.getSuperclass();
        }

        Collections.reverse(parents);
        return parents;
    }

}
