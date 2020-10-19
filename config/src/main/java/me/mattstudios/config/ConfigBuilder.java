package me.mattstudios.config;

import me.mattstudios.config.internal.ConfigManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public final class ConfigBuilder {

    @Nullable
    private Class<? extends ConfigHolder> holder;
    @NotNull
    private final File file;

    protected ConfigBuilder(@NotNull final File file) {
        this.file = file;
    }

    public ConfigBuilder setHolder(@NotNull final Class<? extends ConfigHolder> clazz) {
        holder = clazz;
        return this;
    }

    public Config build() {
        Objects.requireNonNull(holder, "Holder cannot be null");
        return new ConfigManager(file, holder);
    }

}
