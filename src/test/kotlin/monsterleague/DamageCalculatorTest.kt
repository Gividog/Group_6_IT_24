package monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.*
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.PhysicalAttack
import monsterleague.gamelogic.attacks.SpecialAttack
import org.assertj.core.api.Assertions.assertThat

class DamageCalculatorTest : AnnotationSpec() {

  private val dummyType = Type.WATER

  private val dummyAttack1 =
    PhysicalAttack("Punch", AttackKinds.PHYSICAL, dummyType, 100, 35, 10)

  private val dummyAttack2 =
    SpecialAttack("Hydro Pump", AttackKinds.SPECIAL, dummyType, 100, 55, 3)

  private var dummyStatus = Status.CONFUSED

  private var dummyBaseStats1 = BaseStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    specialAttack = 30,
    specialDefense = 40,
  )

  private var dummyBattleStats1 = BattleStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    statusEffect = dummyStatus,
    specialAttack = 30,
    specialDefense = 40
  )

  private var dummyBaseStats2 = BaseStats(
    healthPoints = 100,
    initiative = 10,
    attack = 80,
    defense = 40,
    specialAttack = 30,
    specialDefense = 40,
  )

  private var dummyBattleStats2 = BattleStats(
    healthPoints = 100,
    initiative = 10,
    attack = 80,
    defense = 40,
    statusEffect = dummyStatus,
    specialAttack = 30,
    specialDefense = 40
  )

  private val dummyMonster1 = Monster(
    name = "Monster1",
    type = Type.FIRE,
    baseStats = dummyBaseStats1,
    battleStats = dummyBattleStats1,
    attacks = listOf(dummyAttack1),
  )

  private val dummyMonster2 = Monster(
    name = "Monster2",
    type = Type.WATER,
    baseStats = dummyBaseStats1,
    battleStats = dummyBattleStats1,
    attacks = listOf(dummyAttack2)
  )

  private val dummyMonster3 = Monster(
    name = "Monster3",
    type = Type.GRASS,
    baseStats = dummyBaseStats2,
    battleStats = dummyBattleStats2,
    attacks = listOf(dummyAttack1),
  )

  private val dummyMonster4 = Monster(
    name = "Monster4",
    type = Type.NORMAL,
    baseStats = dummyBaseStats2,
    battleStats = dummyBattleStats2,
    attacks = listOf(dummyAttack1),
  )

  /**
   * calculateEfficiency() tests
   */

  @Test
  fun `test calculateEfficiency returns 2,0 when attack is efficient`() {
    val attackingMonster = dummyMonster1
    val defendingMonster = dummyMonster3
    val efficiency =
      DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateEfficiency()
    assertThat(efficiency).isEqualTo(2.0)
  }

  @Test
  fun `test calculateEfficiency returns 0,5 when attack is inefficient`() {
    val attackingMonster = dummyMonster1
    val defendingMonster = dummyMonster2
    val efficiency =
      DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateEfficiency()
    assertThat(efficiency).isEqualTo(0.5)
  }

  @Test
  fun `test calculateEfficiency returns 1,0 when attack is neither efficient nor inefficient`() {
    val attackingMonster = dummyMonster4
    val defendingMonster = dummyMonster1
    val efficiency =
      DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateEfficiency()
    assertThat(efficiency).isEqualTo(1.0)
  }

  /**
   * criticalStrike() tests
   */

  @Test
  fun `criticalStrike is 2,0`() {
    val attackingMonster = dummyMonster4
    val defendingMonster = dummyMonster1
    val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateCriticalStrike { 0.05 }
    assertThat(result).isEqualTo(1.0)
  }

  @Test
  fun `criticalStrike is 1,0`() {
    val attackingMonster = dummyMonster4
    val defendingMonster = dummyMonster1
    val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateCriticalStrike { 0.03 }
    assertThat(result).isEqualTo(2.0)
  }

  @Test
  fun `criticalStrike is set to random`() {
    val attackingMonster = dummyMonster4
    val defendingMonster = dummyMonster1
    val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateCriticalStrike()
    assertThat(result).isBetween(1.0, 2.0)
  }

  /**
   * calculateDamage() tests
   */

  @Test
  fun `defenseStat is equal to specialDefense of defendingMonster` () {
    val attackingMonster = dummyMonster2
    val defendingMonster = dummyMonster1

    val damage = DamageCalculator(attackingMonster, defendingMonster, dummyAttack2).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(6)
  }

  @Test
  fun `defenseStat is equal to defense of defendingMonster` () {
    val attackingMonster = dummyMonster3
    val defendingMonster = dummyMonster1

    val damage = DamageCalculator(attackingMonster, defendingMonster, dummyAttack1).calculateDamage(criticalHit = 2.0, random = 1.0)
    assertThat(damage).isEqualTo(8)

  }

  @Test
  fun `attackStat is equal to specialAttack of attackingMonster` () {
    val attackingMonster = dummyMonster1
    val defendingMonster = dummyMonster2

    val attack = dummyAttack2

    val damage = DamageCalculator(attackingMonster,defendingMonster,attack).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(1)
  }

  @Test
  fun `attackStat is equal to attack of attackingMonster` () {
    val attackingMonster = dummyMonster1
    val defendingMonster = dummyMonster2

    val attack = dummyAttack1

    val damage = DamageCalculator(attackingMonster,defendingMonster,attack).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(1)

  }

  @Test
  fun `randomNumber and criticalHit are calculated randomly`(){
    val attackingMonster = dummyMonster1
    val defendingMonster = dummyMonster2

    val attack = dummyAttack1

    val damage = DamageCalculator(attackingMonster,defendingMonster,attack).calculateDamage()

    assertThat(damage).isBetween(1, 25)
  }

}