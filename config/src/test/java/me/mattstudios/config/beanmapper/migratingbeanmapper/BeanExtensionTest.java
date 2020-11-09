package me.mattstudios.config.beanmapper.migratingbeanmapper;

import me.mattstudios.config.SettingsManager;
import me.mattstudios.config.SettingsManagerBuilder;
import me.mattstudios.config.TestUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test for {@link SingleValueToCollectionMapper}.
 */
public class BeanExtensionTest {

    private static final String CONFIG_FILE = "/beanmapper/nested_chat_component.yml";

    @Test
    public void shouldLoadFileAndConvertSingleValuesToCollection() {
        // given
        SettingsManager settingsManager = SettingsManagerBuilder
            .withYamlFile(TestUtils.getJarPath(CONFIG_FILE))
            .configurationData(BeanExtensionSettingsHolder.class)
            .create();

        // when
        BeanExtensionSettingsHolder.CollectionChatComponent result = settingsManager.getProperty(BeanExtensionSettingsHolder.CHAT_COMPONENT);

        // then
        assertThat(result.getColor(), contains("blue"));
        assertThat(result.getExtra(), hasSize(2));
        assertThat(result.getExtra().get(0).getColor(), contains("green"));
        assertThat(result.getExtra().get(0).getText(), equalTo("inner1"));
        assertThat(result.getExtra().get(1).getColor(), contains("blue"));
        assertThat(result.getExtra().get(1).getText(), equalTo("inner2"));
    }
}
