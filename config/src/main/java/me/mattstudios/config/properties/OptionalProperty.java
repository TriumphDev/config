package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class OptionalProperty<T> extends BaseProperty<Optional<T>> {

    @NotNull
    private final Class<T> type;

    public OptionalProperty(@Nullable final T defaultValue, @NotNull final Class<T> type) {
        super(Optional.ofNullable(defaultValue));
        this.type = type;
    }

    @NotNull
    @Override
    public Optional<T> determineValue(@NotNull final YamlManager yamlManager) {
        return Optional.ofNullable(yamlManager.getValue(getPath(), type));
    }

    @NotNull
    @Override
    public String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation) {
        //noinspection unchecked
        final Optional<T> optional = (Optional<T>) value;

        if (optional.isPresent()) {
            final T object = optional.get();
            if (type.equals(String.class)) return indentation + key + ": \"" + object + '"';
            return indentation + key + ": " + object;
        }

        return "";
    }

}
