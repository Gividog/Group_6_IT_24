package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.Randomizer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class RandomizerTest: AnnotationSpec() {

  @Test
  fun `random factor is between 0,85 and 1,0 `(){
      val randomFactor = Randomizer.getRandomNumber()
      assertThat(randomFactor).isBetween(0.85,1.0)
  }

  @Test
  fun `critical Strike is 2,0`(){
    val criticalStrike = Randomizer.getCriticalStrike(0.01)
    assertThat(criticalStrike).isEqualTo(2.0)
  }

  @Test
  fun `criticalStrike is 1,0`(){
    val criticalStrike = Randomizer.getCriticalStrike(0.5)
    assertThat(criticalStrike).isEqualTo(1.0)
  }

  @Test
  fun `hitChance is between 0 and 100`(){
    val hitChance = Randomizer.getHitChance()
    assertThat(hitChance).isBetween(0,100)
  }
}