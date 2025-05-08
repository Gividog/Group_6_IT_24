package monsterleague.gamelogic

class Trainer(
    val name: String,
    val monsters: List<Monster>,
    var activeMonster: Monster,
    var healsRemaining: Int
) {}
//checke dass Active Monster nicht null ist
