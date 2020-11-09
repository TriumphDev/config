package me.mattstudios.config.properties;

import me.mattstudios.config.beanmapper.worldgroup.WorldGroupConfig;
import me.mattstudios.config.properties.inlinearray.StandardInlineArrayConverters;
import me.mattstudios.config.properties.types.PrimitivePropertyType;
import me.mattstudios.config.samples.TestEnum;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static me.mattstudios.config.properties.PropertyInitializer.arrayProperty;
import static me.mattstudios.config.properties.PropertyInitializer.inlineArrayProperty;
import static me.mattstudios.config.properties.PropertyInitializer.listProperty;
import static me.mattstudios.config.properties.PropertyInitializer.mapProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newBeanProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newListProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newLowercaseStringSetProperty;
import static me.mattstudios.config.properties.PropertyInitializer.newProperty;
import static me.mattstudios.config.properties.PropertyInitializer.optionalBooleanProperty;
import static me.mattstudios.config.properties.PropertyInitializer.optionalEnumProperty;
import static me.mattstudios.config.properties.PropertyInitializer.optionalIntegerProperty;
import static me.mattstudios.config.properties.PropertyInitializer.optionalStringProperty;
import static me.mattstudios.config.properties.PropertyInitializer.setProperty;
import static me.mattstudios.config.properties.PropertyInitializer.typeBasedProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Test for {@link PropertyInitializer}.
 */
class PropertyInitializerTest {

    @Test
    void shouldInstantiateProperties() {
        assertThat(newProperty("my.path", true), instanceOf(BooleanProperty.class));
        assertThat(newProperty("my.path", 12), instanceOf(IntegerProperty.class));
        assertThat(newProperty("my.path", -8.4), instanceOf(DoubleProperty.class));
        assertThat(newProperty("my.path", "default"), instanceOf(StringProperty.class));
        assertThat(newProperty(TestEnum.class, "my.path", TestEnum.FIRST), instanceOf(EnumProperty.class));
        assertThat(newListProperty("path", "default", "entries"), instanceOf(StringListProperty.class));
        assertThat(newListProperty("path", Arrays.asList("a1", "a2", "a3")), instanceOf(StringListProperty.class));
        assertThat(newLowercaseStringSetProperty("path", "a", "b", "c"), instanceOf(LowercaseStringSetProperty.class));
        assertThat(newLowercaseStringSetProperty("path", Arrays.asList("5", "7")), instanceOf(LowercaseStringSetProperty.class));
        assertThat(newBeanProperty(WorldGroupConfig.class, "worlds", new WorldGroupConfig()), instanceOf(BeanProperty.class));

        assertThat(optionalBooleanProperty("path"), instanceOf(OptionalProperty.class));
        assertThat(optionalIntegerProperty("path"), instanceOf(OptionalProperty.class));
        assertThat(optionalStringProperty("path"), instanceOf(OptionalProperty.class));
        assertThat(optionalEnumProperty(TestEnum.class, "path"), instanceOf(OptionalProperty.class));
    }

    @Test
    void shouldInstantiateBuilders() {
        assertThat(typeBasedProperty(PrimitivePropertyType.STRING), instanceOf(PropertyBuilder.TypeBasedPropertyBuilder.class));
        assertThat(listProperty(PrimitivePropertyType.INTEGER), instanceOf(PropertyBuilder.ListPropertyBuilder.class));
        assertThat(setProperty(PrimitivePropertyType.FLOAT), instanceOf(PropertyBuilder.SetPropertyBuilder.class));
        assertThat(mapProperty(PrimitivePropertyType.DOUBLE), instanceOf(PropertyBuilder.MapPropertyBuilder.class));
        assertThat(arrayProperty(PrimitivePropertyType.BOOLEAN, Boolean[]::new), instanceOf(PropertyBuilder.ArrayPropertyBuilder.class));
        assertThat(inlineArrayProperty(StandardInlineArrayConverters.FLOAT), instanceOf(PropertyBuilder.InlineArrayPropertyBuilder.class));
    }

    @Test
    void shouldHaveAccessibleNoArgsConstructorForExtensions() {
        // given / when
        new PropertyInitializer() { };

        // then - no exception
    }
}
