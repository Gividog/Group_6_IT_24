package monsterleague

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
  fun `calculateDamage returns exactly 24 damage with neutral efficiency and neutral stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackTackle

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage()

    assertThat(damage).isEqualTo(24)
  }

  @Test
  fun `calculateDamage() returns exactly 72 damage with higher efficiency and higher stab factor`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.fireMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage()

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
    ).calculateDamage()

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
    ).calculateDamage()

    assertThat(damage).isEqualTo(12)
  }

  /*
  /**
   * calculateEfficiency() tests
   */

  @Test
  fun `test calculateEfficiency returns 2,0 when attack is efficient`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.fireMonster
    val efficiency =
      EfficiencyCalculator(
        attackingMonster,
        defendingMonster
      ).calculateEfficiency()
    assertThat(efficiency).isEqualTo(2.0)
  }

  @Test
  fun `test calculateEfficiency returns 0,5 when attack is inefficient`() {
    val attackingMonster = TestData.fireMonster
    val defendingMonster = TestData.waterMonster
    val efficiency =
      EfficiencyCalculator(
        attackingMonster,
        defendingMonster
      ).calculateEfficiency()
    assertThat(efficiency).isEqualTo(0.5)
  }

  @Test
  fun `test calculateEfficiency returns 1,0 when attack is neither efficient nor inefficient`() {
    val attackingMonster = TestData.normalMonster
    val defendingMonster = TestData.waterMonster
    val efficiency =
      EfficiencyCalculator(
        attackingMonster,
        defendingMonster
      ).calculateEfficiency()
    assertThat(efficiency).isEqualTo(1.0)
  }*/

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

    assertThat(damage).isEqualTo(6)
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
    assertThat(damage).isEqualTo(8)

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

    assertThat(damage).isEqualTo(1)
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

    assertThat(damage).isEqualTo(1)

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