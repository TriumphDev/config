package me.mattstudios.config.properties;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public final class ListProperty<T> extends BaseProperty<T> {

    public ListProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        final StringBuilder builder = new StringBuilder();
        //noinspection unchecked
        final List<Object> list = (List<Object>) value;

        builder.append(indentation).append(key).append(":\n");
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            final Object object = iterator.next();
            boolean isString = object instanceof String;

            builder.append(indentation).append("  - ");
            if (isString) builder.append('"');
            builder.append(object);
            if (isString) builder.append('"');
            if (iterator.hasNext()) builder.append("\n");
        }

        return builder.toString();
    }

}
