package me.mattstudios.config.internal.yaml;

import me.mattstudios.config.internal.data.ConfigData;
import me.mattstudios.config.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PropertyScanner {

    @NotNull
    private final Map<String, Object> mappedProperties = new LinkedHashMap<>();

    public PropertyScanner(@NotNull final ConfigData configData) {

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

            dest.put(paths[paths.length - 1], entry.getValue());
        }

        System.out.println("Properties to write - " + mappedProperties);

    }

}
