package me.mattstudios.config

import com.google.gson.Gson
import me.mattstudios.config.annotations.Comment
import me.mattstudios.config.annotations.Description
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property

/**
 * @author Matt
 */

data class Test(
        var name: String = "Matt",
        var version: String = "1.0-SNAPSHOT",
        var number: Int = 5,
        var child: Child = Child()
)

data class Child(
        var name: String = "Child",
        var number: Int = 10
)

val gson = Gson()

fun main() {

    /*val config = Config.from(File("testing-files", "config.yml")).setHolder(Settings::class.java).build()

    println(config.getProperty(Settings.FIRST))
    println(config.getProperty(Settings.SECOND))
    println(config.getProperty(Settings.THIRD))
    println(config.getProperty(Settings.FORTH))
    println(config.getProperty(Settings.FIFTH))
    val test = config.getProperty(Settings.SIXTH)
    println(test)
    println(config.getProperty(Settings.SEVENTH))
    println(config.getProperty(Settings.EIGHT))*/

    val test = gson.toJson(Test())
    println(test)

    val json = "{\"name\":\"Matt\",\"version\":\"1.0-SNAPSHOT\",\"number\":5,\"child\":{\"name\":\"Child\",\"number\":10}}"
    val testDeserialized = gson.fromJson(json, Test::class.java)
    println(testDeserialized)

    /*val settingsManager = ConfigManager()SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))

}

@Description("Description comment will go at the top of file!")
object Settings : ConfigHolder {

    @Path("first")
    @Comment("Comment for first property")
    val FIRST = Property.create("first property")

    @Path("second")
    val SECOND = Property.create(true)

    @Comment("Commenting third property")
    @Path("nested.first")
    val THIRD = Property.create("third property")

    @Path("nested.second.first")
    val FORTH = Property.create(5.5)

    @Comment("Commenting last property")
    @Path("nested.second.second")
    val FIFTH = Property.createOptional("Hello")

    @Path("nested.third")
    val SIXTH = Property.create(listOf(1, 2, 3))

    @Comment("", "Added an empty line above")
    @Path("nested.forth")
    val SEVENTH = Property.create(TestEnum.VALUE1, TestEnum::class.java)

    @Comment("Some comments")
    @Path("nested.fifth")
    val EIGHT = Property.create(mapOf("test" to 5, "more" to 10))

}

public enum class TestEnum {

    VALUE1,
    VALUE2,
    VALUE3;

}

