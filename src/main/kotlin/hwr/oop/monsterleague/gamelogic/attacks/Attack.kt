package monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.attacks.StatusChange
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
  fun defenderStatusChange(): StatusChange? = null
  fun attackerStatusChange(): StatusChange? = null

  fun isSpecial(): Boolean {
    return kind == AttackKinds.SPECIAL
  }
}