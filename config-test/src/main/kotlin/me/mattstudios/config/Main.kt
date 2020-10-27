package me.mattstudios.config

import me.mattstudios.config.annotations.Comment
import me.mattstudios.config.annotations.Description
import me.mattstudios.config.annotations.Path
import me.mattstudios.config.properties.Property
import java.io.File

/**
 * @author Matt
 */

fun main() {

    val config = Config.from(File("testing-files", "config.yml")).setHolder(Settings::class.java).build()
    println(config.getProperty(Settings.FIRST))
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
    val SECOND = Property.create("second property")

    @Comment("Commenting third property")
    @Path("nested.first")
    val THIRD = Property.create("third property")

    @Path("nested.second.first")
    val FORTH = Property.create(5.5)

    @Comment("Commenting last property")
    @Path("nested.second.second")
    val FIFTH = Property.create("fifth property")

}


