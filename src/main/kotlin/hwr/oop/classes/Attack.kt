package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class Attack(val name : String, val type: Type, val category: Category, val power: Int, val accuracy: Int , val powerPoint: Int) {
