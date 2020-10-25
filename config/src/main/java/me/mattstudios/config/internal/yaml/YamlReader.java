package me.mattstudios.config.internal.yaml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public final class YamlReader {

    @NotNull
    private final File file;
    @NotNull
    private final Charset charset;

    @Nullable
    private final Map<String, Object> data;

    public YamlReader(@NotNull final File file) {
        this(file, StandardCharsets.UTF_8);
    }

    public YamlReader(@NotNull final File file, @NotNull final Charset charset) {
        this.file = file;
        this.charset = charset;

        data = loadData();
        System.out.println("Properties from file - " + data);
    }

    private Map<String, Object> loadData() {
        try (final InputStream inputStream = Files.newInputStream(file.toPath());
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset)) {
            return new Yaml().load(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file", e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Top-level is not a map in", e);
        } catch (YAMLException e) {
            throw new RuntimeException("YAML error while trying to load file", e);
        }
    }

    @Nullable
    public <T> T get(@NotNull final String path, @NotNull Class<T> clazz) {
        Object value = data;

        for (final String step : path.split("\\.")) {
            if (!(value instanceof Map)) break;

            value = ((Map<?, ?>) value).get(step);
            if (value == null) return null;
        }

        if (!clazz.isInstance(value)) return null;
        return clazz.cast(value);
    }

}
