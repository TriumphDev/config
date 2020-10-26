package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class StringProperty extends BaseProperty<String> {

    public StringProperty(@NotNull final String defaultValue) {
        super(defaultValue);

    }

    @NotNull
    @Override
    public String determineValue(@NotNull final YamlManager yamlManager) {
        final String value = yamlManager.getString(getPath());
        if (value == null) return getDefaultValue();
        return value;
    }

}
