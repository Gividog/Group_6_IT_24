package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

import java.io.File
import java.nio.charset.Charset
import javax.xml.crypto.Data

@Serializable
class CalculateAttackValues(val attackingMonster : Monster,val defendingMonster : Monster, val chosenAttackNumber: Int ) {



        fun checkIfItsACriticalHit():Int{
            return 0
        }

        fun calculateDamageOfAttack( ): Double{
            /*
            *
            * STAB - Überprüfen ob die  Typen gleich sind: gleich=1.5 sonst 1
            * Type Effectivness überprüfen (Wer attackiert und wer verteidigt)
            * critical hit per zufall festlegen ja:2 sonst 1
            *
            * */
            val critical = checkIfItsACriticalHit()
            val stabFactor = calculateValueOfStab()
            val randomNumber: Double = calculateRandomNumberForDamageCalculation()
            val typeEffectiveness = getValueOfTypeEffectiveness()
            val type2:Double = 1.5

            //eeffective attack + defense = wert von Monster
            // randomNumber )= calculate(randomNumber)

            val damage = ( (((((2.0 * critical/ 5.0)+ 2.0 ) * attackingMonster.attacks[chosenAttackNumber].attackSpecificData.power * attackingMonster.stats.attack / attackingMonster.stats.defense) / 50.0) + 2.0) * stabFactor[chosenAttackNumber] * typeEffectiveness *  randomNumber )
            calculateLeftAmountOfAttack()
            return damage
        }

        fun calculateLeftAmountOfAttack(){
            attackingMonster.attacks[chosenAttackNumber].attackSpecificData.powerPoint -= 1;
        }



        fun calculateValueOfStab():List<Double>{
            val listOfStabFactors = mutableListOf<Double>()
            val monsterType = attackingMonster.type
            for (item in 0 until attackingMonster.attacks.size){
               val factor = if(monsterType == attackingMonster.attacks[item].type){
                            1.5
                        }else{
                            1.0
                }
                listOfStabFactors.add(factor)
            }
            return listOfStabFactors
        }

        fun getValueOfTypeEffectiveness():Double{
            var typeEffectivenessValue = 1.0

            if(attackingMonster.attacks[chosenAttackNumber].type == Type.FIRE){
               typeEffectivenessValue = when(defendingMonster.type){
                    Type.FIRE -> 0.5
                    Type.GRASS-> 2.0
                    Type.WATER ->  0.5
                    else -> 1.0
                }
            }else if (attackingMonster.attacks[chosenAttackNumber].type == Type.WATER){
                typeEffectivenessValue = when(defendingMonster.type){
                    Type.FIRE -> 2.0
                    Type.WATER -> 0.5
                    Type.GRASS -> 0.5
                    Type.GROUND -> 2.0
                    else -> 1.0
                }
            }else if(attackingMonster.attacks[chosenAttackNumber].type == Type.GROUND){
                typeEffectivenessValue = when(defendingMonster.type){
                    Type.FIRE -> 2.0
                    Type.ELECTRIC -> 2.0
                    Type.GRASS -> 0.5
                    else -> 1.0
                }
            }else if(attackingMonster.attacks[chosenAttackNumber].type == Type.GRASS){
                typeEffectivenessValue = when(defendingMonster.type){
                    Type.FIRE -> 0.5
                    Type.WATER -> 2.0
                    Type.GRASS -> 0.5
                    Type.GROUND -> 2.0
                    else -> 1.0
                }
            }else if(attackingMonster.attacks[chosenAttackNumber].type == Type.ELECTRIC){
                typeEffectivenessValue = when(defendingMonster.type){
                    Type.WATER -> 2.0
                    Type.ELECTRIC -> 0.5
                    Type.GRASS -> 0.5
                    Type.GROUND -> 0.0
                    else -> 1.0
                }
            }else if(attackingMonster.attacks[chosenAttackNumber].type == Type.PSYCHIC){
                typeEffectivenessValue = when(defendingMonster.type){
                    Type.PSYCHIC -> 0.5
                    else -> 1.0
                }
            }
            return typeEffectivenessValue
        }


        fun calculateRandomNumberForDamageCalculation():Double{
            val randomNumber = (85..100).random()
            val randomFactorForDamageCalculation = randomNumber/100.0
            return randomFactorForDamageCalculation
        }


    }

