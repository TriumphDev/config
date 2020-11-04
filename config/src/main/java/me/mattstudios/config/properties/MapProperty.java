package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.Indentation;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.Iterator;

public final class MapProperty<T> extends BaseProperty<T> {

    public MapProperty(@NotNull final T defaultValue, @NotNull final Class<T> type) {
        super(defaultValue, type);
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final Indentation indentation) {
        final StringBuilder builder = new StringBuilder();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);

        final Iterator<String> iterator = Arrays.asList(new Yaml(options).dump(value).split("\n")).iterator();

        //builder.append(indentation).append(key).append(":\n");

        while (iterator.hasNext()) {
            //builder.append(indentation).append("  ").append(iterator.next());
            //if (iterator.hasNext()) builder.append("\n");
            break;
        }

        return builder.toString();
    }

}
