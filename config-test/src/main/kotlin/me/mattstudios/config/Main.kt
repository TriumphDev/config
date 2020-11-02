package me.mattstudios.config

import com.google.gson.Gson
import me.mattstudios.config.annotations.Comment
import me.mattstudios.config.annotations.Description
import me.mattstudios.config.annotations.Name
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.internal.bean.PropertyMapper
import me.mattstudios.config.properties.Property
import java.beans.IntrospectionException
import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.util.ArrayList
import java.util.Collections

/**
 * @author Matt
 */

data class Test(
        @Comment("Comment test")
        var name: String = "Matt",
        @Name("version-number")
        var versionNumber: String = "1.0-SNAPSHOT",
        var number: Int = 5,
        var child: Child = Child()
)

data class Child(
        var name: String = "Child",
        var number: Int = 10
)

val gson = Gson()

fun main() {

    /*config = Config.from(File("testing-files", "config.yml")).setHolder(Settings::class.java).build()

    println(config.getProperty(Settings.FIRST))
    println(config.getProperty(Settings.SECOND))
    println(config.getProperty(Settings.THIRD))
    println(config.getProperty(Settings.FORTH))
    println(config.getProperty(Settings.FIFTH))
    val test = config.getProperty(Settings.SIXTH)
    println(test)
    println(config.getProperty(Settings.SEVENTH))
    println(config.getProperty(Settings.EIGHT)*/

    println(gson.toJson(Test()))

    val test = Test()

    PropertyMapper(test)

    /*val settingsManager = ConfigManager()SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))

}

@Description("Description comment will go at the top of file!")
object Settings : ConfigHolder {

    @Path("string")
    @Comment("Comment for first property")
    val FIRST = Property.create("first property")

    @Path("boolean")
    val SECOND = Property.create(true)

    @Comment("Commenting third property")
    @Path("nested.int")
    val THIRD = Property.create(5)

    @Path("nested.second.double")
    val FORTH = Property.create(5.5)

    @Comment("Commenting last property")
    @Path("nested.second.optional-string")
    val FIFTH = Property.createOptional("Hello")

    @Path("nested.list")
    val SIXTH = Property.create(listOf(1, 2, 3))

    @Comment("Added an empty line above")
    @Path("nested.enum")
    val SEVENTH = Property.create(TestEnum.VALUE1)

    @Comment("Some comments")
    @Path("nested.map")
    val EIGHT = Property.create(mapOf("test" to 5, "more" to 10))

    //@Path("nested.bean")
    //val NINE = Property.create(Test())

}

public enum class TestEnum {

    VALUE1,
    VALUE2,
    VALUE3;

}

