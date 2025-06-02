package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import monsterleague.gamelogic.*
import monsterleague.gamelogic.attacks.*

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail


class TrainerTest : AnnotationSpec() {
  /**
   * chooseAttack tests
   */

  @Test
  fun `chosen Attack is Punch`() {
    val attack = TestData.trainerWithTwoMonsters.trainerChooseAttack(TestData.physicalAttackTackle)
    assertThat(attack).isEqualTo(TestData.physicalAttackTackle)
  }

  @Test
  fun `chosen Attack is not available`() {
    val chosenAttack = TestData.physicalAttackSplash
    try {
      TestData.trainerWithTwoMonsters.trainerChooseAttack(chosenAttack)
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * changeActiveMonster tests
   */

  @Test
  fun `the newly selected monster at index 1 (so the second element) gets set as the trainers activeMonster`() {
    TestData.trainerWithTwoMonsters.setMonsters(listOf(TestData.waterMonster, TestData.fireMonster))
    TestData.trainerWithTwoMonsters.setActiveMonster(TestData.waterMonster)
    TestData.trainerWithTwoMonsters.switchActiveMonster(TestData.fireMonster)

    assertThat(TestData.trainerWithTwoMonsters.getActiveMonster()).isEqualTo(TestData.fireMonster)
  }

  @Test
  fun `the new selected active monster doesnt exist in Trainers monster list`() {
    val activeMonster = TestData.trainerWithTwoMonsters.getActiveMonster()
    TestData.trainerWithTwoMonsters.switchActiveMonster(monster = TestData.ghostMonster)
    val newSetActiveMonster = TestData.trainerWithTwoMonsters.getActiveMonster()

    assertThat(newSetActiveMonster).isEqualTo(activeMonster)
  }

  /**
   * healActiveMonster tests
   */

  @Test
  fun `healActiveMonster() heals monster's current hp (50) by 30 percent of its base hp (100) and reduces healsRemaining (0)`() {
    TestData.trainerWithTwoMonsters.getActiveMonster().updateHP(50)
    TestData.trainerWithTwoMonsters.setHealsRemaining(1)

    TestData.trainerWithTwoMonsters.healActiveMonster()

    assertThat(TestData.trainerWithTwoMonsters.getActiveMonstersHP()).isEqualTo(80)
    assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
  }

  @Test
  fun `healActiveMonster() does nothing when no heals remaining`() {
    TestData.trainerWithTwoMonsters.setHealsRemaining(0)

    TestData.trainerWithTwoMonsters.healActiveMonster()

    assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
  }
}