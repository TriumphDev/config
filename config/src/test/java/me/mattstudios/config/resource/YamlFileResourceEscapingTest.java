package me.mattstudios.config.resource;

import me.mattstudios.config.SettingsHolder;
import me.mattstudios.config.SettingsManager;
import me.mattstudios.config.SettingsManagerBuilder;
import me.mattstudios.config.TestUtils;
import me.mattstudios.config.properties.Property;
import me.mattstudios.config.properties.types.PrimitivePropertyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.mattstudios.config.properties.PropertyInitializer.mapProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newListProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests that characters with special significance in YAML are properly escaped from paths and map keys.
 *
 * @see <a href="https://github.com/AuthMe/ConfigMe/issues/134">Issue #134</a>
 * @see YamlFileReaderPathsWithNumberTest
 */
class YamlFileResourceEscapingTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    void shouldReadAndWriteAndReloadWithFile() {
        // given
        Path file = TestUtils.copyFileFromResources("/configurationfiles/config-with-chars-to-escape.yml", temporaryFolder);
        SettingsManager settingsManager = SettingsManagerBuilder
            .withYamlFile(file)
            .configurationData(SampleConfig.class)
            .create();
        // Set value here to make sure saving and reading are in sync with the (un)escaping
        settingsManager.setProperty(SampleConfig.COMPLEX_PATH, 33);
        settingsManager.save();
        settingsManager.reload();

        // when / then
        assertThat(settingsManager.getProperty(SampleConfig.SHAPE), contains("$", "$", "!"));
        Map<String, String> expectedIngredients = new HashMap<>();
        expectedIngredients.put("!", "STICK");
        expectedIngredients.put("$", "EMERALD");
        assertThat(settingsManager.getProperty(SampleConfig.INGREDIENTS), equalTo(expectedIngredients));
        assertThat(settingsManager.getProperty(SampleConfig.COMPLEX_PATH), equalTo(33));
    }

    @Test
    void shouldHandleAllProblematicChars() {
        // given
        Path file = TestUtils.copyFileFromResources("/configurationfiles/config-with-chars-to-escape.yml", temporaryFolder);
        SettingsManager settingsManager = SettingsManagerBuilder
            .withYamlFile(file)
            .configurationData(SampleConfig.class)
            .create();

        Map<String, String> ingredients = Stream.of(
                ",", "|", "[", "]", ":", "&", "%", "!!", "?", "~", "^", " ", "\t", "\\", "\\\\", "\\n", "(", "'")
            .collect(Collectors.toMap(Function.identity(), key -> "Value for " + key));
        settingsManager.setProperty(SampleConfig.INGREDIENTS, new HashMap<>(ingredients));

        settingsManager.save();
        settingsManager.reload();

        // when / then
        assertThat(settingsManager.getProperty(SampleConfig.SHAPE), contains("$", "$", "!"));
        assertThat(settingsManager.getProperty(SampleConfig.INGREDIENTS), equalTo(ingredients));
        assertThat(settingsManager.getProperty(SampleConfig.COMPLEX_PATH), equalTo(0));
    }

    public static final class SampleConfig implements SettingsHolder {

        public static final Property<List<String>> SHAPE = newListProperty("shape");

        public static final Property<Map<String, String>> INGREDIENTS = mapProperty(PrimitivePropertyType.STRING)
            .path("ingredients")
            .build();

        public static final Property<Integer> COMPLEX_PATH = newProperty("0o09.07.,.test'.\\.\\\\", 0);

        private SampleConfig() {
        }
    }
}
