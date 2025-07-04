package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.*
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle

import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds
import hwr.oop.monsterleague.gamelogic.attacks.DamagingAttack
import hwr.oop.monsterleague.gamelogic.trainers.Trainer
import monsterleague.gamelogic.attacks.DragonDance
import monsterleague.gamelogic.attacks.SwordsDance
import java.util.UUID

object TestData {
  val battleUuid = UUID.randomUUID()

  /**
   * Dummy Attacks
   * */

  val physicalAttackTackle =
    DamagingAttack("Tackle", AttackKinds.PHYSICAL, Type.NORMAL, 100, 50, 10)

  val physicalAttackSplash =
    DamagingAttack("Splash", AttackKinds.PHYSICAL, Type.WATER, 100, 50, 10)

  val specialAttackHydroPump =
    DamagingAttack("Hydro Pump", AttackKinds.SPECIAL, Type.WATER, 100, 75, 10)

  val attackWithoutPowerPoints =
    DamagingAttack("NoPower", AttackKinds.SPECIAL, Type.WATER, 100, 75, 0)

  val notExistingAttack =
    DamagingAttack("NotExisting", AttackKinds.SPECIAL, Type.WATER, 100, 75, 65)

  val attackZeroAccuracy =
    DamagingAttack("NoHitChance", AttackKinds.PHYSICAL, Type.NORMAL, 0, 50, 10)

  /**
   * Dummy Stats
   * */

  var baseStatsLowerInitiative = BaseStats(
    healthPoints = 250,
    initiative = 10,
    attack = 100,
    defense = 100,
    specialAttack = 120,
    specialDefense = 120,
  )

  var baseStatsHigherInitiative = BaseStats(
    healthPoints = 250,
    initiative = 20,
    attack = 100,
    defense = 100,
    specialAttack = 120,
    specialDefense = 120,
  )

var baseStatsSwordDancingMonster = BaseStats(
  healthPoints = 250,
  initiative = 20,
  attack = 100,
  defense = 100,
  specialAttack = 120,
  specialDefense = 120,
)

  var baseStatsDragonDancingMonster = BaseStats(
    healthPoints = 250,
    initiative = 30,
    attack = 100,
    defense = 100,
    specialAttack = 150,
    specialDefense = 120,
  )

  var baseStatsScreechingMonster = BaseStats(
    healthPoints = 250,
    initiative = 20,
    attack = 100,
    defense = 90,
    specialAttack = 150,
    specialDefense = 120,
  )

  var battleStatsWithStatus = BattleStats(
    healthPoints = 250,
    initiative = 10,
    attack = 100,
    defense = 100,
    statusEffect = Status.CONFUSED,
    specialAttack = 120,
    specialDefense = 120
  )


  var battleStatsHigherInitiative = BattleStats(
    healthPoints = 150,
    initiative = 30,
    attack = 120,
    defense = 100,
    statusEffect = Status.CONFUSED,
    specialAttack = 100,
    specialDefense = 120
  )

  var battleStatsDefeatedMonster = BattleStats(
    healthPoints = 0,
    initiative = 10,
    attack = 100,
    defense = 100,
    statusEffect = null,
    specialAttack = 120,
    specialDefense = 120
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
    battleStats = battleStatsHigherInitiative,
    attacks = listOf(physicalAttackTackle, specialAttackHydroPump)
  )

  val ghostMonster = Monster(
    name = "Monster3",
    type = Type.GHOST,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle, attackZeroAccuracy)
  )

  val normalMonster = Monster(
    name = "Monster4",
    type = Type.NORMAL,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle, attackWithoutPowerPoints)
  )

  val defeatedMonster = Monster(
    name = "Monster5",
    type = Type.NORMAL,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsDefeatedMonster,
    attacks = listOf(physicalAttackTackle)
  )

  val buffMonsterSwordDancer = Monster(
    name = "SwordDancer",
    type = Type.NORMAL ,
    baseStats = baseStatsSwordDancingMonster,
    battleStats = BattleStats.createBasedOn(baseStatsSwordDancingMonster),
    attacks = listOf(SwordsDance)
  )

  val buffMonsterDragonDancer = Monster(
    name = "DragonDancer",
    type = Type.NORMAL,
    baseStats = baseStatsSwordDancingMonster,
    battleStats = BattleStats.createBasedOn(baseStatsDragonDancingMonster),
    attacks = listOf(DragonDance)
  )

  val buffMonsterScreech = Monster(
    name = "Screech",
    type = Type.NORMAL,
    baseStats = baseStatsScreechingMonster,
    battleStats = BattleStats.createBasedOn(baseStatsScreechingMonster),
    attacks = listOf(DragonDance)
  )

  /**
   * Dummy Trainers
   * */

  val trainerWithTwoMonsters =
    TrainerInBattle(
      "trainer1",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

  val trainerWithOneDefeatedMonster =
    TrainerInBattle(
      "trainer2",
      listOf(waterMonster, defeatedMonster),
      defeatedMonster,
      3
    )

  val trainerWithGhostMonsterLeft =
    TrainerInBattle(
      "trainer4",
      listOf(ghostMonster, defeatedMonster),
      ghostMonster,
      3
    )

  val trainerWithNoHealsRemaining =
    TrainerInBattle(
      "trainer5",
      listOf(ghostMonster, waterMonster, fireMonster),
      waterMonster,
      0
    )

  val trainerWithOnlyDefeatedMonsters =
    TrainerInBattle("trainer3", listOf(defeatedMonster), defeatedMonster, 0)

  val trainerHealsDecrement= TrainerInBattle(
      "trainer5",
      listOf(ghostMonster, waterMonster, fireMonster),
      waterMonster,
      3
  )

  val trainerWithNoPowerPointsAttack = TrainerInBattle(
    "trainer6",
    listOf(normalMonster),
    normalMonster,
    healsRemaining = 3
  )

  /**
   * Dummy basic Trainers
   * */

  val trainerOutOfCombatAsh = Trainer(
    name = "Ash",
    monsters = listOf(normalMonster, waterMonster, ghostMonster)
  )

  val trainerOutOfCombatMisty = Trainer(
    name = "Misty",
    monsters = listOf(waterMonster, fireMonster),
  )
}