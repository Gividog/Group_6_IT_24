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
    val powerPoint: Int
){

    /*
    private fun getJSONFromData(): String? {
        var json: String? = null
        val charset : Charset =  Charsets.UTF_8

        try{
            val attackJsonFile = File("src/main/kotlin/hwr/oop/data/AttackData.json")
            val size = attackJsonFile.length()
            val buffer = ByteArray(size)
        }
    }
    val json = File("src/main/kotlin/hwr/oop/data/AttackData.json").readText()
    val parsedJson = Json.parseToJsonElement(json).jsonObject
*/


    fun checkIfItsACriticalHit(){

    }

    fun calculateDamageOfAttack(
        critical: Int,
        eAttack: Int,
        dAttack: Int ,
        stab: Int,
        type1: Int,
        type2: Int,
        random: Double
    ): Double{
        /*
        *
        * STAB - Überprüfen ob die  Typen gleich sind: gleich=1.5 sonst 1
        * Type Effectivness überprüfen (Wer attackiert und wer verteidigt)
        * critical hit per zufall festlegen ja:2 sonst 1
        *
        * */
        //val stabFactor = getValueOfStab()
        //eeffective attack + defense = wert von Monster

        val damage = ( (((((2 * critical/ 5)+ 2 ) * power * eAttack/dAttack ) / 50) + 2) * stab  * type1 * type2 * random )

        return damage
    }

    fun calculateLeftAmountOfAttack(){
        //Power Point = Amount of Attacks
    }

    var test = Type.FIRE
    fun getValueOfStab(monsterType:Type, attackType:Type):Double{
        var stabFactor : Double = 1.0
        if(monsterType == attackType){
            stabFactor = 1.5
        }
        return stabFactor
    }

    fun getValueOfTypeEffectivenessOne(){

    }

    fun getValueOfTypeEffectivenessTwo(){

    }

    fun calculateRandomNumberForDamageCalculation(){

    }
}
