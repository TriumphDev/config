package me.mattstudios.config.internal.yaml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public final class YamlManager {

    @NotNull
    private final File file;
    @Nullable
    private Yaml yaml;

    public YamlManager(@NotNull final File file) {
        this.file = file;

        yaml = createFile();
    }

    private Yaml createFile() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        return new Yaml(options);
    }


}
