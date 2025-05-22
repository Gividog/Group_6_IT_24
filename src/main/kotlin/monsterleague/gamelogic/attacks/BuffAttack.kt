package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

class BuffAttack (
    override val name: String,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
) : Attack {

    // max buff 6 min -2

    fun defenseBuff(){
        //Amy
    }

    fun attackBuff(){
        //Erik
    }

    fun initiativeBuff(){
    //Liesa
    }

    fun specialDefenseBuff(){
        //Nikita
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
