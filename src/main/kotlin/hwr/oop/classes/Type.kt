package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
enum class Type {
    FIRE, WATER, GRASS, ELECTRIC, NORMAL, GROUND, PSYCHIC
}