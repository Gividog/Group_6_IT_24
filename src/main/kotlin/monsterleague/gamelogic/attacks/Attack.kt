package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.ComboAttack
import monsterleague.gamelogic.attacks.PhysicalAttack
import monsterleague.gamelogic.attacks.SpecialAttack
import monsterleague.gamelogic.attacks.StatusAttack

interface Attack {
    val name: String
    val type: Type
}