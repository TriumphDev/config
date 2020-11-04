package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.Indentation;
import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class BeanProperty<T> extends BaseProperty<T> {

    public BeanProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

    @NotNull
    @Override
    public T determineValue(final @NotNull YamlManager yamlManager) {
        final T object = getDefaultValue();

        for (final Method method : object.getClass().getDeclaredMethods()) {
            System.out.println(method);
        }

        return getDefaultValue();
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final Indentation indentation) {
        return "";
    }

}
