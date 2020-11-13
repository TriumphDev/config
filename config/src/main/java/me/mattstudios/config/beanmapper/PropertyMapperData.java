package me.mattstudios.config.beanmapper;

import me.mattstudios.config.annotations.TargetObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

public final class PropertyMapperData {

    @Nullable
    private final PropertyMapper propertyMapper;
    @NotNull
    private final Map<Class<?>, Method> mappers = new HashMap<>();

    public PropertyMapperData(@Nullable final PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
        collectMappers();
    }

    private void collectMappers() {
        if (propertyMapper == null) return;

        final Method[] methods = propertyMapper.getClass().getDeclaredMethods();
        for (final Method method : methods) {
            if (!method.isAnnotationPresent(TargetObject.class)) continue;
            if (!Modifier.isPublic(method.getModifiers())) continue;

            final Class<?> target = method.getAnnotation(TargetObject.class).value();

            if (!method.getReturnType().equals(target)) continue;
            if (method.getParameterCount() != 1) continue;

            final Type rawParameterType = method.getGenericParameterTypes()[0];
            if (!(rawParameterType instanceof ParameterizedType)) continue;
            final ParameterizedType parameterizedType = (ParameterizedType) rawParameterType;
            if (!Map.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) continue;
            final Type[] types = parameterizedType.getActualTypeArguments();
            if (!types[0].equals(String.class)) continue;
            if (!(types[1] instanceof WildcardType) && !types[1].equals(Object.class)) continue;

            mappers.put(target, method);
        }
    }

    public boolean hasMapper(@NotNull final Class<?> type) {
        final Method mapper = mappers.get(type);
        if (mapper == null && type.isInterface()) throw new ConfigMeMapperException("Oh hell nah!");
        return mapper != null;
    }

    public Object mapProperty(@NotNull final Class<?> type, @NotNull Object value) {
        final Method mapper = mappers.get(type);
        if (mapper == null) return null;

        try {
            return mapper.invoke(propertyMapper, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
