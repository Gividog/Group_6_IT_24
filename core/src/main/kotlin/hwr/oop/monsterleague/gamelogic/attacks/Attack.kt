package monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.attacks.StatChange
import monsterleague.gamelogic.Type

interface Attack {
  val name: String
  val kind: AttackKinds
  val type: Type
  val accuracy: Int
  val power: Int
  val powerPoints: Int
  fun defenderDamage(): Int = 0
  fun attackerDamage(): Int = 0
  fun defenderStatChange(): StatChange? = null
  fun attackerStatChange(): StatChange? = null

  fun isSpecial(): Boolean {
    return kind == AttackKinds.SPECIAL
  }

  fun getPowerPoints() : Int {
    return powerPoints
  }

  fun getName() : String {
    return name
  }
}