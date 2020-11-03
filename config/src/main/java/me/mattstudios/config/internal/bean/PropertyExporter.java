package me.mattstudios.config.internal.bean;

import me.mattstudios.config.internal.bean.types.Exporter;
import me.mattstudios.config.internal.bean.types.NormalExporter;
import me.mattstudios.config.internal.bean.types.StringExporter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PropertyExporter {

    private static final Map<Class<?>, Exporter> exporters = new HashMap<>();

    static {
        exporters.put(String.class, new StringExporter());

        final Exporter normalExporter = new NormalExporter();
        exporters.put(int.class, normalExporter);
        exporters.put(Integer.class, normalExporter);
        exporters.put(float.class, normalExporter);
        exporters.put(Float.class, normalExporter);
        exporters.put(double.class, normalExporter);
        exporters.put(Double.class, normalExporter);
        exporters.put(boolean.class, normalExporter);
        exporters.put(Boolean.class, normalExporter);
        exporters.put(short.class, normalExporter);
        exporters.put(Short.class, normalExporter);
        exporters.put(long.class, normalExporter);
        exporters.put(Long.class, normalExporter);
    }

    private PropertyExporter() {}

    public static String exportValue(@NotNull final Object value, @NotNull final Class<?> type, @NotNull final String indentation) {
        // Exports simple properties
        String simpleValue = getSimpleObject(value);
        if (simpleValue != null) {
            return simpleValue;
        }

        // Exports list properties
        simpleValue = getCollectionObject(value, type, indentation);
        if (simpleValue != null) {
            return simpleValue;
        }

        // Bean
        Map<String, Object> mappedBean = new LinkedHashMap<>();
        for (BeanPropertyDescription property : beanDescriptionFactory.getAllProperties(value.getClass())) {
            Object exportValueOfProperty = toExportValue(property.getValue(value));
            if (exportValueOfProperty != null) {
                mappedBean.put(property.getName(), exportValueOfProperty);
            }
        }

        return "";
    }

    private static String getSimpleObject(@NotNull final Object value) {
        final Class<?> type = value.getClass();
        final Exporter exporter;
        if (Enum.class.isAssignableFrom(type)) exporter = exporters.get(String.class);
        else exporter = exporters.get(type);

        if (exporter == null) return null;
        return exporter.export(value, type);
    }

    private static String getCollectionObject(@NotNull final Object value, @NotNull final Class<?> type, @NotNull final String indentation) {
        if (type != List.class) return null;

        final StringBuilder builder = new StringBuilder();
        //noinspection unchecked
        final Iterable<Object> list = (Iterable<Object>) value;

        builder.append("\n");
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            builder.append(indentation).append("  - ").append(getSimpleObject(iterator.next()));
            if (iterator.hasNext()) builder.append("\n");
        }

        return builder.toString();
    }

}
