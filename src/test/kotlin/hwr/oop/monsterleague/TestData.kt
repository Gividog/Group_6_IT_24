package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.DamagingAttack
import monsterleague.gamelogic.attacks.SpecialAttack
import java.util.UUID

object TestData {
  val battleUuid = UUID.randomUUID()

  /**
   * Dummy Attacks
   * */

  val physicalAttackTackle =
    DamagingAttack("Tackle", AttackKinds.PHYSICAL, Type.NORMAL, 100, 35, 10)

  val physicalAttackSplash =
    DamagingAttack("Splash", AttackKinds.PHYSICAL, Type.WATER, 100, 35, 10)

  val specialAttackHydroPump =
    DamagingAttack("Hydro Pump", AttackKinds.SPECIAL, Type.WATER, 100, 55, 10)

  /**
   * Dummy Stats
   * */

  var baseStatsLowerInitiative = BaseStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    specialAttack = 30,
    specialDefense = 40,
  )

  var baseStatsHigherInitiative = BaseStats(
    healthPoints = 100,
    initiative = 20,
    attack = 20,
    defense = 30,
    specialAttack = 30,
    specialDefense = 40,
  )

  var baseStatsHigherAttack = BaseStats(
    healthPoints = 100,
    initiative = 10,
    attack = 80,
    defense = 30,
    specialAttack = 30,
    specialDefense = 40,
  )

  var battleStatsWithStatus = BattleStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    statusEffect = Status.CONFUSED,
    specialAttack = 30,
    specialDefense = 40
  )

  var battleStatsHigherAttack = BattleStats(
    healthPoints = 100,
    initiative = 10,
    attack = 80,
    defense = 40,
    statusEffect = Status.CONFUSED,
    specialAttack = 30,
    specialDefense = 40
  )

  var battleStatsDefeatedMonster = BattleStats(
    healthPoints = 0,
    initiative = 10,
    attack = 20,
    defense = 30,
    statusEffect = null,
    specialAttack = 30,
    specialDefense = 40
  )

  /**
   * Dummy Monsters
   * */

  val waterMonster = Monster(
    name = "Monster1",
    type = Type.WATER,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle),
  )

  val fireMonster = Monster(
    name = "Monster2",
    type = Type.FIRE,
    baseStats = baseStatsHigherInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val ghostMonster = Monster(
    name = "Monster3",
    type = Type.GHOST,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val normalMonster = Monster(
    name = "Monster4",
    type = Type.NORMAL,
    baseStats = baseStatsHigherAttack,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val defeatedMonster = Monster(
    name = "Monster5",
    type = Type.NORMAL,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsDefeatedMonster,
    attacks = listOf(physicalAttackTackle)
  )

  /**
   * Dummy Trainers
   * */

  val trainerWithTwoMonsters =
    TrainerInBattle("trainer1", listOf(waterMonster, fireMonster), fireMonster, 3)

  val trainerWithOneDefeatedMonster =
    TrainerInBattle("trainer2", listOf(waterMonster, defeatedMonster), defeatedMonster, 3)

  val trainerWithOnlyDefeatedMonsters =
    TrainerInBattle("trainer3", listOf(defeatedMonster), defeatedMonster, 0)
}