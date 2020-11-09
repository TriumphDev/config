package me.mattstudios.config

import com.google.gson.Gson
import java.io.File

/**
 * @author Matt
 */

val gson = Gson()

fun main() {

    val config = Config.from(File("testing-files", "config.yml")).setHolder(Settings::class.java).build()

    println(config.getProperty(Settings.FIRST))
    println(config.getProperty(Settings.SECOND))
    println(config.getProperty(Settings.THIRD))
    println(config.getProperty(Settings.FORTH))
    println(config.getProperty(Settings.FIFTH))
    val test = config.getProperty(Settings.SIXTH2)
    println(test)
    println(config.getProperty(Settings.SEVENTH))
    println(config.getProperty(Settings.EIGHTH))

    //println(gson.toJson(Test()))

    /*val settingsManager = ConfigManager()SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))
}


/*@Description("Description comment will go at the top of file!")
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
    val SIXTH = Property.create(listOf(1, 2))

    @Path("list2")
    val SIXTH2 = Property.create(listOf("Hello", "Mate"))

    @Comment("Added an empty line above")
    @Path("nested.enum")
    val SEVENTH = Property.create(TestEnum.VALUE1)

    @Path("bean")
    val EIGHTH = Property.create(Test())

}*/

data class Test(
        var name: String = "Matt",
        var list: Map<String, Child> = mapOf("first" to Child("Jeu"), "second" to Child("heu")),
        var child: Child = Child("Shit")
)

data class Child(
        var name: String = "Child test",
        var number: Int = 10
)

public enum class TestEnum {

    VALUE1,
    VALUE2,
    VALUE3;

}

