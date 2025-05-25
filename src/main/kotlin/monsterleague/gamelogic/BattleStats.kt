package monsterleague.gamelogic

class BattleStats (
    private var hp: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var statusEffect: Status?,
    var specialDefense : Int,
    private var specialAttack : Int,
){


    fun defenseBuff(){
       //
    }

    fun attackBuff(){
        //Erik
    }

    fun initiativeBuff(monster: Monster){
        val initiative = initiative
    }

    fun specialDefenseBuff(monster: Monster){
        val current = specialDefense
        specialDefense = (current ).toInt()
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