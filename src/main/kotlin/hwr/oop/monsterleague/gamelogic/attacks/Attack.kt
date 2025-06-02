package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

interface Attack {
    val name: String
    val kind: AttackKinds
    val type: Type
    val accuracy: Int
    val power: Int
    val powerPoints: Int
}