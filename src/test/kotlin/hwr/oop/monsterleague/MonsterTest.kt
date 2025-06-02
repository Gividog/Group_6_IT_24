package monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.AnnotationSpec.Test
import monsterleague.gamelogic.*
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.PhysicalAttack
import org.assertj.core.api.Assertions.assertThat

class MonsterTest : AnnotationSpec() {
  private val dummyType = Type.WATER

  private val dummyAttack =
    PhysicalAttack("Punch", AttackKinds.PHYSICAL, dummyType, 100, 35, 10)

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

  private val dummyMonster1 = Monster(
    name = "Monster1",
    type = dummyType,
    baseStats = dummyBaseStats,
    battleStats = dummyBattleStats,
    attacks = listOf(dummyAttack),
  )

  /**
   * takeDamage() tests
   */

  @Test
  fun `Monster takes 30 damage`() {
    val damage = 30

    dummyMonster1.takeDamage(damage)

    assertThat(dummyMonster1.getHP()).isEqualTo(70)
  }
}