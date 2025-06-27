package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.Randomizer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class RandomizerTest: AnnotationSpec() {

  @Test
  fun `random Numbers`(){
    val number = Randomizer().getRandomNumber()
    println(number)
  }

  /**
   * Query tests
   */

  @Test
  fun `getRandomNumber() returns a random Double`() {
    val number = Randomizer().getRandomNumber()

    assertThat(number).isNotNull()
  }

  @Test
  fun `getCriticalStrike() returns 1_0 when the criticalHitChance isn't hit`() {
    val number = Randomizer().getCriticalStrike(0.5)

    assertThat(number).isEqualTo(1.0)
  }

  @Test
  fun `getCriticalStrike() returns 2_0 when the criticalHitChance is hit`() {
    val number = Randomizer().getCriticalStrike(0.01)

    assertThat(number).isEqualTo(2.0)
  }

  @Test
  fun `getCriticalStrike() returns 2_0 or 1_0 depending on whether the criticalHitChance is hit or not`() {
    val number = Randomizer().getCriticalStrike()

    assertThat(number).isBetween(1.0,2.0)
  }

  @Test
  fun `getHitChance() returns a random number between 0 and 100`() {
    val number = Randomizer().getHitChance()

    assertThat(number).isNotNull()
  }
}