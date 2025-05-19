package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

interface Attack {
    val name: String
    val type: Type
    val accuracy: Int
    val power: Int
    val powerPoints: Int
}