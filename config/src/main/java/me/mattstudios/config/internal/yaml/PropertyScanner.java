package me.mattstudios.config.internal.yaml;

import me.mattstudios.config.internal.data.ConfigData;
import me.mattstudios.config.internal.yaml.elements.CommentElement;
import me.mattstudios.config.internal.yaml.elements.Element;
import me.mattstudios.config.internal.yaml.elements.KeyElement;
import me.mattstudios.config.internal.yaml.elements.PropertyElement;
import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PropertyScanner {

    @NotNull
    private final List<Element> elements = new ArrayList<>();
    private final Yaml yaml;

    public PropertyScanner(@NotNull final ConfigData configData, @NotNull final Yaml yaml) {
        this.yaml = yaml;

        final Map<String, Object> mappedProperties = mapProperties(configData);

        checkElements(mappedProperties, 0);

        for (final Element element : elements) {
            System.out.println(element.getValue());
        }

    }

    private void checkElements(@NotNull final Map<String, Object> mappedProperties, final int indentation) {
        for (final Map.Entry<String, Object> entry : mappedProperties.entrySet()) {
            final Object value = entry.getValue();

            if (!(value instanceof Map)) {
                final Property<?> property = (Property<?>) entry.getValue();

                if (property.getComments() != null) {
                    elements.add(new CommentElement(indentation));
                }

                elements.add(new PropertyElement(indentation, entry.getKey(), entry.getValue()));
                continue;
            }

            elements.add(new KeyElement(indentation, entry.getKey()));

            checkElements((Map<String, Object>) value, indentation + 2);

        }
    }

    private Map<String, Object> mapProperties(@NotNull final ConfigData configData) {
        final Map<String, Object> mappedProperties = new LinkedHashMap<>();

        for (final Map.Entry<Property<?>, Object> entry : configData.getProperties().entrySet()) {
            final Property<?> property = entry.getKey();
            final String[] paths = property.getPath().split("\\.");

            Map<String, Object> dest = mappedProperties;

            for (int i = 0; i < paths.length - 1; i++) {

                Object temp = dest.get(paths[i]);

                if (temp == null) {
                    // We did not see this branch yet
                    Map<String, Object> next = new LinkedHashMap<>();
                    dest.put(paths[i], next);
                    dest = next;
                    continue;
                }

                if (temp instanceof Map) {
                    dest = (Map<String, Object>) temp;
                }

            }

            dest.put(paths[paths.length - 1], property);
        }

        return mappedProperties;
    }

}
