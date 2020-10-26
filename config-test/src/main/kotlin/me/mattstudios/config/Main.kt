package me.mattstudios.config

import me.mattstudios.config.annotations.Comment
import me.mattstudios.config.annotations.Description
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.StringProperty
import java.io.File

/**
 * @author Matt
 */

fun main() {

    val config = Config.from(File("testing-files", "config.yml")).setHolder(Settings::class.java).build()

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
    val FIRST = StringProperty("first property")

    @Path("second")
    val SECOND = StringProperty("second property")

    @Comment("Commenting")
    @Path("nested.first")
    val THIRD = StringProperty("third property")

    @Path("nested.second.first")
    val FORTH = StringProperty("forth property")

    @Comment("Commenting")
    @Path("nested.second.second")
    val FIFTH = StringProperty("fifth property")

}


