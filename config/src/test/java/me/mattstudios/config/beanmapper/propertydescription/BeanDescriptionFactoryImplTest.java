package me.mattstudios.config.beanmapper.propertydescription;


import me.mattstudios.config.beanmapper.ConfigMeMapperException;
import me.mattstudios.config.samples.beanannotations.AnnotatedEntry;
import me.mattstudios.config.samples.beanannotations.BeanWithEmptyName;
import me.mattstudios.config.samples.beanannotations.BeanWithNameClash;
import me.mattstudios.config.samples.inheritance.Child;
import me.mattstudios.config.samples.inheritance.Middle;
import me.mattstudios.config.utils.TypeInformation;
import me.mattstudios.config.TestUtils;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static me.mattstudios.config.TestUtils.verifyException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test for {@link BeanDescriptionFactoryImpl}.
 */
class BeanDescriptionFactoryImplTest {

    @Test
    void shouldReturnWritableProperties() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        Collection<BeanPropertyDescription> descriptions = factory.getAllProperties(SampleBean.class, parentProperty);

        // then
        assertThat(descriptions, hasSize(2));
        assertThat(getDescription("size", descriptions).getTypeInformation(), equalTo(new TypeInformation(int.class)));
        assertThat(getDescription("name", descriptions).getTypeInformation(), equalTo(new TypeInformation(String.class)));
    }

    @Test
    void shouldReturnEmptyListForNonBeanClass() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when / then
        assertThat(factory.getAllProperties(List.class, parentProperty), empty());
    }

    @Test
    void shouldHandleBooleanMethodsAndMatchWithFields() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        List<BeanPropertyDescription> properties = new ArrayList<>(factory.getAllProperties(BooleanTestBean.class, parentProperty));

        // then
        assertThat(properties, hasSize(4));
        assertThat(TestUtils.transform(properties, BeanPropertyDescription::getName),
                   containsInAnyOrder("active", "isField", "empty", "isNotMatched"));

        // First two elements can be mapped to fields, so check their order. For the two unknown ones, we don't make any guarantees
        assertThat(properties.get(0).getName(), equalTo("active"));
        assertThat(properties.get(1).getName(), equalTo("isField"));
    }

    @Test
    void shouldNotConsiderTransientFields() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        Collection<BeanPropertyDescription> properties = factory.getAllProperties(BeanWithTransientFields.class, parentProperty);

        // then
        assertThat(properties, hasSize(2));
        assertThat(TestUtils.transform(properties, BeanPropertyDescription::getName), contains("name", "mandatory"));
    }

    @Test
    void shouldBeAwareOfInheritanceAndRespectOrder() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        Collection<BeanPropertyDescription> properties = factory.getAllProperties(Middle.class, parentProperty);

        // then
        assertThat(properties, hasSize(3));
        assertThat(TestUtils.transform(properties, BeanPropertyDescription::getName), contains("id", "name", "ratio"));
    }

    @Test
    void shouldLetChildFieldsOverrideParentFields() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        Collection<BeanPropertyDescription> properties = factory.getAllProperties(Child.class, parentProperty);

        // then
        assertThat(properties, hasSize(5));
        assertThat(TestUtils.transform(properties, BeanPropertyDescription::getName),
                   contains("id", "temporary", "name", "ratio", "importance"));
    }

    @Test
    void shouldUseExportName() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when
        Collection<BeanPropertyDescription> properties = factory.getAllProperties(AnnotatedEntry.class, parentProperty);

        // then
        assertThat(properties, hasSize(2));
        assertThat(TestUtils.transform(properties, BeanPropertyDescription::getName),
                   contains("id", "has-id"));
    }

    @Test
    void shouldThrowForMultiplePropertiesWithSameName() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when / then
        TestUtils.verifyException(
            () -> factory.getAllProperties(BeanWithNameClash.class, parentProperty),
            ConfigMeMapperException.class,
            "multiple properties with name 'threshold'");
    }

    @Test
    void shouldThrowForWhenExportNameIsNullForProperty() {
        // given
        BeanDescriptionFactory factory = new BeanDescriptionFactoryImpl();

        // when / then
        TestUtils.verifyException(
            () -> factory.getAllProperties(BeanWithEmptyName.class, parentProperty),
            ConfigMeMapperException.class,
            "may not be empty");
    }

    private static BeanPropertyDescription getDescription(String name,
                                                          Collection<BeanPropertyDescription> descriptions) {
        for (BeanPropertyDescription description : descriptions) {
            if (name.equals(description.getName())) {
                return description;
            }
        }
        throw new IllegalArgumentException(name);
    }

    private static final class SampleBean {

        private String name;
        private int size;
        private long longField; // static "getter" method
        private UUID uuid = UUID.randomUUID(); // no setter

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public UUID getUuid() {
            return uuid;
        }

        public static long getLongField() {
            // Method with normal getter name is static!
            return 0;
        }

        public void setLongField(long longField) {
            this.longField = longField;
        }
    }

    private static final class BooleanTestBean {
        private boolean isEmpty;
        private Boolean isReference;
        private boolean active;
        private String isString;
        private boolean isField;
        private boolean notMatched;

        public boolean isEmpty() {
            return isEmpty;
        }

        public void setEmpty(boolean empty) {
            isEmpty = empty;
        }

        public Boolean isReference() { // "is" getter only supported for primitive boolean
            return isReference;
        }

        public void setReference(Boolean isReference) {
            this.isReference = isReference;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String isString() { // "is" only supported for boolean
            return isString;
        }

        public void setString(String isString) {
            this.isString = isString;
        }

        public boolean getIsField() {
            return isField;
        }

        public void setIsField(boolean field) {
            this.isField = field;
        }

        // -----------------
        // notMatched: creates a valid property "isNotMatched" picked up by the introspector,
        // but we should not match this to the field `notMatched`.
        // -----------------
        public boolean getIsNotMatched() {
            return notMatched;
        }

        public void setIsNotMatched(boolean notMatched) {
            this.notMatched = notMatched;
        }
    }

    private static final class BeanWithTransientFields {
        private String name;
        private transient long tempId;
        private transient boolean isSaved;
        private boolean isMandatory;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTempId() {
            return tempId;
        }

        @Transient
        public void setTempId(long tempId) {
            this.tempId = tempId;
        }

        @Transient
        public boolean isSaved() {
            return isSaved;
        }

        public void setSaved(boolean saved) {
            isSaved = saved;
        }

        public boolean isMandatory() {
            return isMandatory;
        }

        public void setMandatory(boolean mandatory) {
            isMandatory = mandatory;
        }
    }
}
