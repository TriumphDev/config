package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;

public final class NumberProperty extends BaseProperty<Number> {

    public NumberProperty(@NotNull final Number defaultValue) {
        super(defaultValue);

    }

    @NotNull
    @Override
    public Number determineValue(@NotNull final YamlManager yamlManager) {
        final Number value = yamlManager.getValue(getPath(), Number.class);
        if (value == null) return getDefaultValue();
        return value;
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value) {
        return key + ": " + value;
    }

}
