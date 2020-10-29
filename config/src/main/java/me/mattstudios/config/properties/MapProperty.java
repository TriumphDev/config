package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.Iterator;

public final class MapProperty<T> extends BaseProperty<T> {

    @NotNull
    private final Class<T> type;

    public MapProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue);
        this.type = type;
    }

    @NotNull
    @Override
    public T determineValue(@NotNull final YamlManager yamlManager) {
        final T value = yamlManager.getValue(getPath(), type);
        if (value == null) return getDefaultValue();
        return value;
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        final StringBuilder builder = new StringBuilder();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);

        final Iterator<String> iterator = Arrays.asList(new Yaml(options).dump(value).split("\n")).iterator();

        builder.append(indentation).append(key).append(":\n");

        while (iterator.hasNext()) {
            builder.append(indentation).append("  ").append(iterator.next());
            if (iterator.hasNext()) builder.append("\n");
        }

        return builder.toString();
    }

}
