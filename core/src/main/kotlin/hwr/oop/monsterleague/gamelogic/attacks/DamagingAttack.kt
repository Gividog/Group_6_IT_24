package hwr.oop.monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds

class DamagingAttack(
  override val name: String,
  override val kind: AttackKinds,
  override val type: Type,
  override val accuracy: Int,
  override val  power: Int,
  override val powerPoints: Int,
) : Attack {



}