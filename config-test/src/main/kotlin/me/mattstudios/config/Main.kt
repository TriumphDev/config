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

    /*val settingsManager = ConfigManager()SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))

}

@Description("Description comment!")
object Settings : ConfigHolder {

    @Path("first")
    @Comment("Comment for first property")
    val FIRST = TestProperty<String>("hello")

    @Path("second")
    val SECOND = TestProperty<String>("hello")

    @Path("third")
    val THIRD = TestProperty<String>("hello")

}

class TestProperty<T>(val value: String) : Property<T> {


}
