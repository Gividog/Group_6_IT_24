package monsterleague

import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.PhysicalAttack
import monsterleague.gamelogic.attacks.SpecialAttack

object TestData {
    val Punch =
        PhysicalAttack("Punch", AttackKinds.PHYSICAL, Type.WATER, 100, 35, 10)

    val HydroPump =
        SpecialAttack("Hydro Pump", AttackKinds.SPECIAL, Type.WATER, 100, 55, 3)
}