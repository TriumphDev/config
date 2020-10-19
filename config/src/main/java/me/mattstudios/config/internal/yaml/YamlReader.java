package me.mattstudios.config.internal.yaml;

import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class YamlReader {

    private final Path path;
    private final Charset charset;
    private final Map<String, Object> root;

    public YamlReader(Path path) {
        this(path, StandardCharsets.UTF_8);
    }

    public YamlReader(Path path, Charset charset) {
        this.path = path;
        this.charset = charset;
        this.root = loadFile();
    }

    public Object getObject(String path) {
        if (path.isEmpty()) {
            return root;
        }

        Object node = root;
        String[] keys = path.split("\\.");
        for (String key : keys) {
            node = getEntryIfIsMap(key, node);
            if (node == null) {
                return null;
            }
        }
        return node;
    }


    public String getString(String path) {
        return getTypedObject(path, String.class);
    }

    public Integer getInt(String path) {
        Number n = getTypedObject(path, Number.class);
        return (n == null)
                ? null
                : n.intValue();
    }

    public Double getDouble(String path) {
        Number n = getTypedObject(path, Number.class);
        return (n == null)
                ? null
                : n.doubleValue();
    }

    public Boolean getBoolean(String path) {
        return getTypedObject(path, Boolean.class);
    }

    public List<?> getList(String path) {
        return getTypedObject(path, List.class);
    }

    public boolean contains(String path) {
        return getObject(path) != null;
    }

    public Set<String> getKeys(boolean onlyLeafNodes) {
        Set<String> allKeys = new LinkedHashSet<>();
        collectKeysIntoSet("", root, allKeys, onlyLeafNodes);
        return allKeys;
    }

    public Set<String> getChildKeys(String path) {
        Object object = getObject(path);
        if (object instanceof Map) {
            String pathPrefix = path.isEmpty() ? "" : path + ".";
            return ((Map<String, Object>) object).keySet().stream()
                    .map(childPath -> pathPrefix + childPath)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Collections.emptySet();
    }

    private void collectKeysIntoSet(String path, Map<String, Object> map, Set<String> result, boolean onlyLeafNodes) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String childPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
            if (!onlyLeafNodes || isLeafValue(entry.getValue())) {
                result.add(childPath);
            }

            if (entry.getValue() instanceof Map) {
                collectKeysIntoSet(childPath, (Map) entry.getValue(), result, onlyLeafNodes);
            }
        }
    }

    private static boolean isLeafValue(Object o) {
        return !(o instanceof Map) || ((Map) o).isEmpty();
    }


    protected Map<String, Object> loadFile() {
        try (InputStream is = Files.newInputStream(path);
             InputStreamReader isr = new InputStreamReader(is, charset)) {
            Map<Object, Object> rootMap = new Yaml().load(isr);
            return normalizeMap(rootMap);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file '" + path + "'", e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Top-level is not a map in '" + path + "'", e);
        } catch (YAMLException e) {
            throw new RuntimeException("YAML error while trying to load file '" + path + "'", e);
        }
    }

    protected Map<String, Object> normalizeMap(@Nullable Map<Object, Object> map) {
        return null;
    }

    protected final Path getPath() {
        return path;
    }

    protected final Map<String, Object> getRoot() {
        return root;
    }

    @Nullable
    protected <T> T getTypedObject(String path, Class<T> clazz) {
        Object value = getObject(path);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }

    @Nullable
    private static Object getEntryIfIsMap(String key, Object value) {
        if (value instanceof Map<?, ?>) {
            return ((Map<?, ?>) value).get(key);
        }
        return null;
    }

}
