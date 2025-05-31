package monsterleague

import monsterleague.gamelogic.*
import monsterleague.gamelogic.attacks.*

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail


class TrainerTest : AnnotationSpec() {
  private val dummyType = Type.WATER

  private val dummyAttack =
    PhysicalAttack("Punch", AttackKinds.PHYSICAL, dummyType, 100, 35, 10)

  private val dummyAttack2 =
    SpecialAttack("Special", AttackKinds.PHYSICAL, dummyType, 100, 35, 10)
  private var dummyStatus = Status.CONFUSED

  private var dummyBaseStats = BaseStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    specialAttack = 30,
    specialDefense = 40,
  )

  private var dummyBattleStats = BattleStats(
    healthPoints = 100,
    initiative = 10,
    attack = 20,
    defense = 30,
    statusEffect = dummyStatus,
    specialAttack = 30,
    specialDefense = 40
  )

  private var dummyBattleStats2 = BattleStats(
    healthPoints = 0,
    initiative = 10,
    attack = 20,
    defense = 30,
    statusEffect = null,
    specialAttack = 30,
    specialDefense = 40
  )

  private val dummyMonster1 = Monster(
    name = "Monster1",
    type = dummyType,
    baseStats = dummyBaseStats,
    battleStats = dummyBattleStats,
    attacks = listOf(dummyAttack),
  )

  private val dummyMonster2 = Monster(
    name = "Monster2",
    type = dummyType,
    baseStats = dummyBaseStats,
    battleStats = dummyBattleStats,
    attacks = listOf(dummyAttack)
  )

  private val dummyMonster3 = Monster(
    name = "Monster3",
    type = dummyType,
    baseStats = dummyBaseStats,
    battleStats = dummyBattleStats2,
    attacks = listOf(dummyAttack)
  )

  private val dummyTrainer =
    Trainer("trainer1", listOf(dummyMonster1, dummyMonster2), dummyMonster2, 3)

  /**
   * chooseAttack tests
   */

  @Test
  fun `chosen Attack is Punch`() {
    val attack = dummyTrainer.trainerChooseAttack(dummyAttack)
    assertThat(attack).isEqualTo(dummyAttack)
  }

  @Test
  fun `chosen Attack is not available`() {
    val chosenAttack = dummyAttack2
    try {
      dummyTrainer.trainerChooseAttack(chosenAttack)
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
    dummyTrainer.monsters = listOf(dummyMonster1, dummyMonster2)
    dummyTrainer.activeMonster = dummyMonster1

    dummyTrainer.switchActiveMonster(dummyMonster2)

    assertThat(dummyTrainer.activeMonster).isEqualTo(dummyMonster2)
  }

  @Test
  fun `the new selected active monster doesnt exist in Trainers monster list`() {
    val activeMonster = dummyTrainer.activeMonster
    dummyTrainer.switchActiveMonster(monster = dummyMonster3)
    val newSetActiveMonster = dummyTrainer.activeMonster

    assertThat(newSetActiveMonster).isEqualTo(activeMonster)
  }

  /**
   * healActiveMonster tests
   */

  @Test
  fun `healActiveMonster() heals monster's current hp (50) by 30 percent of its base hp (100) and reduces healsRemaining (0)`() {
    dummyTrainer.activeMonster.battleStats.updateHP(50)
    dummyTrainer.healsRemaining = 1

    dummyTrainer.healActiveMonster()

    assertThat(dummyTrainer.activeMonster.battleStats.getHP()).isEqualTo(80)
    assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
  }

  @Test
  fun `healActiveMonster() does nothing when no heals remaining`() {
    dummyTrainer.healsRemaining = 0

    dummyTrainer.healActiveMonster()

    assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
  }

  /**
   * Message tests
   * */

  @Test
  fun `setNotReadToFight() is set to false when called`() {
    val trainer = dummyTrainer

    dummyTrainer.setNotReadyToFight()

    assertThat(dummyTrainer.getReadyToFight())
  }
}