package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.attacks.ComboAttack
import monsterleague.gamelogic.attacks.PhysicalAttack
import monsterleague.gamelogic.attacks.SpecialAttack
import monsterleague.gamelogic.attacks.StatusAttack

class Attack(
    val physicalAttack: PhysicalAttack,
    private val specialAttack: SpecialAttack,
    private val statusAttack: StatusAttack,
    private val comboAttack: ComboAttack
) {
    interface Attack {


    }
}