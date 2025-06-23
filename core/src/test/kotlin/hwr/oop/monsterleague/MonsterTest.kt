package monsterleague

import hwr.oop.monsterleague.TestData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MonsterTest : AnnotationSpec() {
  /**
   * takeDamage() tests
   */

  @Test
  fun `Monster takes 30 damage`() {
    val monster = TestData.waterMonster
    val damage = 30

    monster.takeDamage(damage)

    assertThat(monster.getHP()).isEqualTo(70)
  }
  @Test
  fun `getAttacks returns the correct list of attacks`() {
    val monster = TestData.fireMonster
    val attacks = monster.getAttacks()

    assertThat(attacks).containsExactlyElementsOf(TestData.fireMonster.getAttacks())
  }

}