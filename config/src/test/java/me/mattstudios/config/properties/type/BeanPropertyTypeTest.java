package me.mattstudios.config.properties.type;

import me.mattstudios.config.beanmapper.DefaultMapper;
import me.mattstudios.config.beanmapper.Mapper;
import me.mattstudios.config.beanmapper.command.Command;
import me.mattstudios.config.demo.beans.CoordinateSystem;
import me.mattstudios.config.demo.beans.Location;
import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.BeanPropertyType;
import me.mattstudios.config.utils.TypeInformation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link BeanPropertyType}.
 */
class BeanPropertyTypeTest {

    @Test
    void shouldReturnConvertedValue() {
        // given
        TypeInformation typeInformation = new TypeInformation(Location.class);
        Mapper mapper = mock(Mapper.class);
        Location location = new Location();
        location.setLatitude(360f);
        location.setLongitude(123f);
        BeanPropertyType<Location> beanPropertyType = new BeanPropertyType<>(typeInformation, mapper);
        ConvertErrorRecorder errorRecorder = new ConvertErrorRecorder();
        given(mapper.convertToBean("test_bean", typeInformation, errorRecorder)).willReturn(location);

        // when
        Object result = beanPropertyType.convert("test_bean", errorRecorder);

        // then
        assertThat(result, equalTo(location));
    }

    @Test
    void shouldReturnValueAsExportValue() {
        // given
        TypeInformation typeInformation = new TypeInformation(Location.class);
        Location location = new Location();
        location.setLatitude(360f);
        location.setLongitude(123f);
        location.setCoordinateType(CoordinateSystem.NAD);
        BeanPropertyType<Location> beanPropertyType = new BeanPropertyType<>(typeInformation, DefaultMapper.getInstance());

        // when
        Object result = beanPropertyType.toExportValue(location);

        // then
        assertThat(result, instanceOf(Map.class));
        Map<String, Object> map = (Map) result;
        assertThat(map.keySet(), containsInAnyOrder("latitude", "longitude", "coordinateType"));
        assertThat(map.get("latitude"), equalTo(360f));
        assertThat(map.get("longitude"), equalTo(123f));
        assertThat(map.get("coordinateType"), equalTo("NAD"));
    }

    @Test
    void shouldInitializeWithDefaultMapper() throws NoSuchFieldException, IllegalAccessException {
        // given / when
        BeanPropertyType<Command> type = BeanPropertyType.of(Command.class);

        // then
        Field field = BeanPropertyType.class.getDeclaredField("mapper");
        field.setAccessible(true);
        Object mapperValue = field.get(type);
        assertThat(mapperValue, sameInstance(DefaultMapper.getInstance()));
    }
}
