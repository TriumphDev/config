package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.PropertyValue;
import me.mattstudios.config.properties.types.PrimitivePropertyType;
import me.mattstudios.config.resource.PropertyReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static me.mattstudios.config.TestUtils.isErrorValueOf;
import static me.mattstudios.config.TestUtils.isValidValueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

/**
 * Test for {@link ListProperty}.
 */
@ExtendWith(MockitoExtension.class)
class ListPropertyTest {

    @Mock
    private PropertyReader reader;

    @Test
    void shouldReturnValueFromResource() {
        // given
        Property<List<Integer>> property = new ListProperty<>("list", PrimitivePropertyType.INTEGER);
        given(reader.getList("list")).willReturn((List) Arrays.asList(3, 5, 7.0));

        // when
        PropertyValue<List<Integer>> result = property.determineValue(reader);

        // then
        assertThat(result, isValidValueOf(Arrays.asList(3, 5, 7)));
    }

    @Test
    void shouldReturnDefaultValue() {
        // given
        Property<List<Integer>> property = new ListProperty<>("list", PrimitivePropertyType.INTEGER, 8, 9, 10);
        given(reader.getList("list")).willReturn(null);

        // when
        PropertyValue<List<Integer>> result = property.determineValue(reader);

        // then
        assertThat(result, isErrorValueOf(Arrays.asList(8, 9, 10)));
    }

    @Test
    void shouldReturnValueAsExportValue() {
        // given
        Property<List<Integer>> property = new ListProperty<>("list", PrimitivePropertyType.INTEGER, Arrays.asList(-2, 16));

        // when
        Object result = property.toExportValue(Arrays.asList(128, -256, 512));

        // then
        assertThat(result, equalTo(Arrays.asList(128, -256, 512)));
    }
}
