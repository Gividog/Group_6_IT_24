package monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.attacks.StatusChange
import monsterleague.gamelogic.Type

object SwordsDance : Attack {
  override val name: String = "Swords Dance"
  override val kind: AttackKinds = AttackKinds.BUFF
  override val type: Type = Type.NORMAL
  override val accuracy: Int = 50
  override val power : Int = 0
  override val powerPoints: Int = 3

  override fun attackerStatusChange() = StatusChange.Buff(
    attackSteps = 2,
  )
}

object DragonDance : Attack {
  override val name: String = "Dragon Dance"
  override val kind: AttackKinds = AttackKinds.BUFF
  override val type: Type = Type.NORMAL
  override val accuracy: Int = 50
  override val power: Int = 0
  override val powerPoints: Int = 3

  override fun attackerStatusChange() = StatusChange.Buff(
    attackSteps = 1,
    initiativeSteps = 1,
  )
}

object Screech : Attack {
  override val name: String = "Screech"
  override val kind: AttackKinds = AttackKinds.DEBUFF
  override val type: Type = Type.NORMAL
  override val accuracy: Int = 60
  override val power: Int = 0
  override val powerPoints: Int = 3

  override fun defenderStatusChange() = StatusChange.Buff(
    defenseSteps = -2,
  )
}

object Haze : Attack {
  override val name: String = "Haze"
  override val kind: AttackKinds = AttackKinds.BUFF
  override val type: Type = Type.NORMAL
  override val accuracy: Int = 50
  override val power: Int = 0
  override val powerPoints: Int = 3

  override fun attackerStatusChange() = StatusChange.Clear
}
