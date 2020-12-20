package me.mattstudios.config

import me.mattstudios.config.annotations.TargetObject
import me.mattstudios.config.beanmapper.PropertyMapper

/**
 * @author Matt
 */
class CustomMapper : PropertyMapper {

    @TargetObject(Some::class)
    fun mapTest(path: String, value: Map<String, Any>): Some {
        println(path)
        println(value)
        val name = value["name"]
        if (name == null || name != "Matt") return Child()
        return Matt()
    }

}