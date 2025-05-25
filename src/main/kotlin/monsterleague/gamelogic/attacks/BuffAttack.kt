package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Type

class BuffAttack (
    override val name: String,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
    private val multiplyer: Float
) : Attack {

    // max buff 6 min -2

    fun defenseBuff(){
        //Amy
    }

    fun attackBuff(){
        //Erik
    }

    fun initiativeBuff(monster: Monster){
     val initiative = monster.battleStats.initiative
        initiative * multiplyer
    }

    fun specialDefenseBuff(monster: Monster){
        val current = monster.battleStats.specialDefense
        monster.battleStats.specialDefense = (current * multiplyer).toInt()
    }

    fun specialAttackBuff(){

    }

    fun defenseDeBuff(){

    }

    fun attackDeBuff(){

    }

    fun initiativeDeBuff(){

    }

    fun specialDefenseDeBuff(){

    }

    fun specialAttackDeBuff(){

    }
}
    // beinhaltet sowohl Debuff-Attacken als auch Buff-Attacken (?)
