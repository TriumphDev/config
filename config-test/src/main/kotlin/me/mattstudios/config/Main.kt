package me.mattstudios.config

import me.mattstudios.config.properties.NullableProperty

/**
 * @author Matt
 */
fun main() {

    val settingsManager = ConfigManager()/*SettingsManagerBuilder.withYamlFile(File("testing-files", "config.yml"))
            .useDefaultMigrationService()
            .configurationData(createConfiguration(Settings::class.java))
            .create()*/

    //println(settingsManager.getProperty(Settings.HELLO))

}

object Settings : ConfigHolder {

    //val HELLO: Property<String> = newProperty("hello", "hello")

}

class Test<T> : NullableProperty<T>

