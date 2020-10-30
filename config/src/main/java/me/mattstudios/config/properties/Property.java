package me.mattstudios.config.properties;

import me.mattstudios.config.internal.yaml.YamlManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Property<T> {

    @NotNull
    String getPath();

    @NotNull
    T getDefaultValue();

    @NotNull
    T determineValue(@NotNull final YamlManager yamlManager);

    @Nullable
    List<String> getComments();

    @NotNull
    String getExportValue(@NotNull final String key, @NotNull final Object value, @NotNull final String indentation);

    @NotNull
    @Contract("_ -> new")
    static Property<String> create(@NotNull final String defaultValue) {
        return new SimpleProperty<>(defaultValue, String.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Integer> create(final int defaultValue) {
        return new SimpleProperty<>(defaultValue, Integer.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Short> create(final short defaultValue) {
        return new SimpleProperty<>(defaultValue, Short.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Double> create(final double defaultValue) {
        return new SimpleProperty<>(defaultValue, Double.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Float> create(final float defaultValue) {
        return new SimpleProperty<>(defaultValue, Float.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Boolean> create(final boolean defaultValue) {
        return new SimpleProperty<>(defaultValue, Boolean.class);
    }

    @NotNull
    @Contract("_ -> new")
    static <T extends Enum<T>> Property<T> create(@NotNull final T defaultValue) {
        //noinspection unchecked
        final Class<T> type = (Class<T>) defaultValue.getClass();
        return new EnumProperty<>(defaultValue, type);
    }

    @NotNull
    @Contract("_ -> new")
    static <T> Property<List<T>> create(@NotNull final List<T> defaultValue) {
        //noinspection unchecked
        return new ListProperty<>(defaultValue, (Class<List<T>>) (Object) List.class);
    }

    @NotNull
    @Contract("_ -> new")
    static <T> Property<List<T>> create(@NotNull final T... defaultValues) {
        //noinspection unchecked
        return new ListProperty<>(Arrays.asList(defaultValues), (Class<List<T>>) (Object) List.class);
    }

    @NotNull
    @Contract("_ -> new")
    static <T> Property<Map<String, T>> create(@NotNull final Map<String, T> defaultValue) {
        //noinspection unchecked
        return new MapProperty<>(defaultValue, (Class<Map<String, T>>) (Object) Map.class);
    }

    @NotNull
    @Contract("_ -> new")
    static Property<Optional<String>> createOptional(@Nullable final String defaultValue) {
        return new OptionalProperty<>(defaultValue, String.class);
    }

}
