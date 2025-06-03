package monsterleague

import hwr.oop.monsterleague.TestData
import hwr.oop.monsterleague.gamelogic.calculators.EfficiencyCalculator
import hwr.oop.monsterleague.gamelogic.Randomizer
import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.*
import org.assertj.core.api.Assertions.assertThat

class DamageCalculatorTest : AnnotationSpec() {
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
  }

  /**
   * calculateDamage() tests
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

  @Test
  fun `randomNumber and criticalHit are calculated randomly`() {
    val attackingMonster = TestData.waterMonster
    val defendingMonster = TestData.normalMonster
    val attack = TestData.physicalAttackSplash

    val damage = DamageCalculator(
      attackingMonster,
      defendingMonster,
      attack
    ).calculateDamage()

    assertThat(damage).isBetween(1, 25)
  }

}