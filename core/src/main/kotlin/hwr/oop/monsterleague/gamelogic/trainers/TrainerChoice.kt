package hwr.oop.monsterleague.gamelogic.trainers

import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.attacks.Attack

sealed interface TrainerChoice {
  fun precedence(): Int = Int.MAX_VALUE

  data class AttackChoice(
    val attackingMonster: Monster,
    val selectedAttack: Attack,
    val targetedMonster: Monster,
  ) : TrainerChoice {
    override fun precedence() = attackingMonster.getInitiativeStat()
  }

  data class SwitchChoice(
    val outMonster: Monster,
    val inMonster: Monster,
  ) : TrainerChoice

  data class HealChoice(
    val monster: Monster,
  ) : TrainerChoice

  data class SurrenderChoice(
    val surrenderingTrainer : TrainerInBattle,
  ): TrainerChoice


}