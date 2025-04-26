package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

import java.io.File
import java.nio.charset.Charset
import javax.xml.crypto.Data

@Serializable
data class Attack(
    val type: Type,
    val category: Category,
    val attackSpecificData: AttackSpecificData
)

@Serializable
data class AttackSpecificData(
    val name: String,
    val power: Int,
    val accuracy: Int,
    var powerPoint: Int
)

@Serializable
data class ListOfAttacks(
    val attacks : Map<String, Attack>
)



