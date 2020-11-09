package me.mattstudios.config.properties;

import me.mattstudios.config.properties.types.PrimitivePropertyType;
import me.mattstudios.config.resource.PropertyReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.mattstudios.config.TestUtils.isErrorValueOf;
import static me.mattstudios.config.TestUtils.isValidValueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

/**
 * Test for {@link TypeBasedProperty}.
 */
@ExtendWith(MockitoExtension.class)
class TypeBasedPropertyTest {

    @Mock
    private PropertyReader reader;

    @Test
    void shouldReturnValueFromResource() {
        // given
        Property<String> property = new TypeBasedProperty<>("common.path", "default", PrimitivePropertyType.STRING);
        given(reader.getObject("common.path")).willReturn("some string");

        // when / then
        assertThat(property.determineValue(reader), isValidValueOf("some string"));
    }

    @Test
    void shouldReturnDefaultValue() {
        // given
        Property<String> property = new TypeBasedProperty<>("common.path", "default", PrimitivePropertyType.STRING);

        // when / then
        assertThat(property.determineValue(reader), isErrorValueOf("default"));
    }

    @Test
    void shouldReturnValueAsExportValue() {
        // given
        Property<String> property = new TypeBasedProperty<>("common.path", "default", PrimitivePropertyType.STRING);
        String given = "given string";

        // when / then
        assertThat(property.toExportValue(given), equalTo(given));
    }

}
