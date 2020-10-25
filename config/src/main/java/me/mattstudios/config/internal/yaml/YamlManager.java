package me.mattstudios.config.internal.yaml;

import me.mattstudios.config.internal.data.ConfigData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public final class YamlManager {

    @NotNull
    private final File file;
    @Nullable
    private Yaml yaml;

    @NotNull
    private final YamlReader yamlReader;
    private final ConfigData configData;

    public YamlManager(@NotNull final File file, @NotNull final ConfigData configData) {
        this.file = file;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        yaml = createFile();
        yamlReader = new YamlReader(file);
        this.configData = configData;
    }

    private Yaml createFile() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        return new Yaml(options);
    }

    @Nullable
    public String getString(@NotNull String path) {
        return yamlReader.get(path, String.class);
    }

    public void getInt(@NotNull String path) {
        final Integer number = yamlReader.get(path, Integer.class);
        System.out.println(number);
    }

    public void getMap(@NotNull String path) {
    }

    public void writeProperties() {
        new PropertyScanner(configData);
    }

}
