package hwr.oop.monsterleague

import hwr.oop.monsterleague.TestData
import hwr.oop.monsterleague.gamelogic.calculators.EfficiencyCalculator
import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class CalculatorTest : AnnotationSpec() {
  /**
   * calculateDamage() tests
   */

  @Test
  fun `calculateDamage returns exactly 21 damage with neutral efficiency and neutral stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackTackle

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(24)
  }

  @Test
  fun `calculateDamage() returns exactly 65 damage with higher efficiency and higher stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.fireMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(72)
  }

  @Test
  fun `calculateDamage() returns exactly 18 damage with low efficiency and higher stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.waterMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(18)
  }

  @Test
  fun `calculateDamage() returns exactly 20 damage with low efficiency and low stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.waterMonster
    val attack = TestData.physicalAttackTackle

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(12)
  }


  /**
   * right stat is used tests
   */

  @Test
  fun `defenseStat is equal to specialDefense of defendingMonster`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      TestData.specialAttackHydroPump
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(53)
  }

  @Test
  fun `defenseStat is equal to defense of defendingMonster`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 2.0, random = 1.0)
    assertThat(damage).isEqualTo(66)

  }

  @Test
  fun `attackStat is equal to specialAttack of attackingMonster`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.specialAttackHydroPump

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(53)
  }

  @Test
  fun `attackStat is equal to attack of attackingMonster`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage(criticalHit = 1.0, random = 1.0)

    assertThat(damage).isEqualTo(36)

  }

  /**
   * simpleDamageCalculation() tests
   */

  @Test
  fun `simpleDamageCalculation returns exactly 24 damage with neutral efficiency and neutral stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackTackle

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).simpleDamageCalculation()

    assertThat(damage).isEqualTo(24)
  }

  @Test
  fun `simpleDamageCalculation returns exactly 72 damage with higher efficiency and higher stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.fireMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).simpleDamageCalculation()

    assertThat(damage).isEqualTo(72)
  }

  @Test
  fun `simpleDamageCalculation returns exactly 18 damage with low efficiency and higher stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.waterMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).simpleDamageCalculation()

    assertThat(damage).isEqualTo(18)
  }

  @Test
  fun `simpleDamageCalculation returns exactly 20 damage with low efficiency and low stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.waterMonster
    val attack = TestData.physicalAttackTackle

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).simpleDamageCalculation()

    assertThat(damage).isEqualTo(12)
  }
}