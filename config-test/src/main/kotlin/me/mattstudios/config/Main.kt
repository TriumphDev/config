package me.mattstudios.config

import com.google.gson.Gson
import jdk.jfr.Description
import me.mattstudios.config.annotations.Comment
import me.mattstudios.config.annotations.Name
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property
import java.io.File

/**
 * @author Matt
 */

val gson = Gson()

fun main() {

    val config = SettingsManager
            .from(File("testing-files", "config.yml"))
            .configurationData(Settings::class.java)
            .create()

    println(config.getProperty(Settings.EIGHTH))

    //println(gson.toJson(Test()))

    /*val settingsManager = ConfigManager()SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))
}


@Description("Description comment will go at the top of file!")
object Settings : SettingsHolder {

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

    /*@Path("nested.list")
    val SIXTH = Property.create(listOf(1, 2))*/

    @Path("list2")
    val SIXTH2 = Property.create(listOf("Hello", "Mate"))

    /*@Comment("Added an empty line above")
    @Path("nested.enum")
    val SEVENTH = Property.create(TestEnum.VALUE1)*/

    @Path("bean")
    val EIGHTH = Property.create(Test())

}

data class Test(
        @Comment("This is a comment to parent class")
        var name: String = "Matt",
        var map: Map<String, Child> = mapOf("first" to Child("Jeu", listOf("Hello", "there")), "second" to Child("heu")),
        @Name("not-child")
        var child: Child = Child("Shit")
)

data class Child(
        @Comment("This is a comment to child class")
        var name: String = "Child test",
        @Name("fuck")
        var list: List<String> = listOf("Hello"),
        var number: Int = 10
)

public enum class TestEnum {

    VALUE1,
    VALUE2,
    VALUE3;

}

