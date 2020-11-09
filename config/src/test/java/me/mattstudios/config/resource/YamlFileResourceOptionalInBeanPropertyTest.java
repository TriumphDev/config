package me.mattstudios.config.resource;


import me.mattstudios.config.beanmapper.Mapper;
import me.mattstudios.config.beanmapper.MapperImpl;
import me.mattstudios.config.beanmapper.command.ExecutionDetails;
import me.mattstudios.config.beanmapper.command.Executor;
import me.mattstudios.config.beanmapper.command.optionalproperties.ComplexCommand;
import me.mattstudios.config.beanmapper.command.optionalproperties.ComplexCommandConfig;
import me.mattstudios.config.configurationdata.ConfigurationData;
import me.mattstudios.config.configurationdata.ConfigurationDataBuilder;
import me.mattstudios.config.properties.BeanProperty;
import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;

import static me.mattstudios.config.TestUtils.copyFileFromResources;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests that bean properties with {@code Optional} fields can be exported properly.
 *
 * @see <a href="https://github.com/AuthMe/ConfigMe/issues/51">Issue #51</a>
 */
class YamlFileResourceOptionalInBeanPropertyTest {

    private static BeanProperty<ComplexCommandConfig> commandConfigProperty = new BeanProperty<>(
        ComplexCommandConfig.class, "commandconfig", new ComplexCommandConfig());

    @TempDir
    public Path temporaryFolder;

    @Test
    void shouldSaveOptionalFieldsProperly() {
        // given
        Path file = copyFileFromResources("/beanmapper/optionalproperties/complex-commands.yml", temporaryFolder);
        PropertyResource resource = new YamlFileResource(file);
        PropertyReader reader = resource.createReader();
        Mapper mapper = new MapperImpl();
        ComplexCommandConfig result = mapper.convertToBean(reader.getObject("commandconfig"), ComplexCommandConfig.class, new ConvertErrorRecorder());
        result.getCommands().put("shutdown", createShutdownCommand());
        ConfigurationData configurationData = createConfigurationData();
        configurationData.setValue(commandConfigProperty, result);

        // when
        resource.exportProperties(configurationData);

        // then
        PropertyResource resourceAfterSave = new YamlFileResource(file);
        ConvertErrorRecorder errorRecorderAfterSave = new ConvertErrorRecorder();
        ComplexCommandConfig commandConfig = mapper.convertToBean(
            resourceAfterSave.createReader().getObject("commandconfig"), ComplexCommandConfig.class, errorRecorderAfterSave);
        assertThat(errorRecorderAfterSave.isFullyValid(), equalTo(true));

        assertThat(commandConfig.getCommands().keySet(),
            containsInAnyOrder("shutdown", "greet", "block_invalid", "log_admin", "launch"));
        ComplexCommand shutDownCmd = commandConfig.getCommands().get("shutdown");
        assertThat(shutDownCmd.getCommand(), equalTo("app shutdown"));
        assertThat(shutDownCmd.getDoubleOptional(), equalTo(Optional.of(3.0)));
        assertThat(shutDownCmd.getExecution().isOptional(), equalTo(true));

        assertThat(commandConfig.getCommands().get("greet").getCommand(), equalTo("hello $user"));
        assertThat(commandConfig.getCommands().get("block_invalid").getExecution().getPrivileges(), contains("system.kick"));
    }

    private static ConfigurationData createConfigurationData() {
        return ConfigurationDataBuilder.createConfiguration(Collections.singletonList(commandConfigProperty));
    }

    private static ComplexCommand createShutdownCommand() {
        ExecutionDetails executionDetails = new ExecutionDetails();
        executionDetails.setExecutor(Executor.CONSOLE);
        executionDetails.setOptional(true);
        executionDetails.setImportance(1.0);
        executionDetails.setPrivileges(Collections.emptySet());

        ComplexCommand command = new ComplexCommand();
        command.setExecution(executionDetails);
        command.setCommand("app shutdown");
        command.setDoubleOptional(Optional.of(3.0));
        command.setNameHasLength(Optional.empty());
        command.setNameStartsWith(Optional.empty());
        command.setTestEnumProperty(Optional.empty());
        return command;
    }
}
