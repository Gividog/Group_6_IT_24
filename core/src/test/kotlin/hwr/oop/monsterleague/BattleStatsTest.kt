package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.attacks.StatChange
import io.kotest.core.spec.style.AnnotationSpec
import hwr.oop.monsterleague.gamelogic.BattleStats
import hwr.oop.monsterleague.gamelogic.Status
import org.assertj.core.api.Assertions.assertThat

class BattleStatsTest: AnnotationSpec() {

  @Test
  fun `create battlesStats based on BaseStats`(){
    val baseStats = TestData.baseStatsLowerInitiative
    val battleStats = BattleStats.createBasedOn(baseStats)

    assertThat(battleStats.getHP()).isEqualTo(baseStats.getHealthPoints())
    assertThat(battleStats.getInitiative()).isEqualTo(baseStats.getInitiative())
    assertThat(battleStats.getAttack()).isEqualTo(baseStats.getAttack())
    assertThat(battleStats.getDefense()).isEqualTo(baseStats.getDefense())
    assertThat(battleStats.getSpecialDefense()).isEqualTo(baseStats.getSpecialDefense())
    assertThat(battleStats.getStatusEffect()).isEqualTo(null)
    assertThat(battleStats.getSpecialAttack()).isEqualTo(baseStats.getSpecialAttack())
  }

  @Test
  fun `StatusChange gets applied`(){
    val battleStats = TestData.waterMonster.getBattleStats()
    val buff = StatChange.Buff(
      attackSteps = 2,
      specialAttackSteps = 3
    )
    battleStats.applyChange(buff, TestData.waterMonster)

    assertThat(battleStats.getHP()).isEqualTo(250)
    assertThat(battleStats.getInitiative()).isEqualTo(10)
    assertThat(battleStats.getAttack()).isEqualTo(160)
    assertThat(battleStats.getDefense()).isEqualTo(100)
    assertThat(battleStats.getStatusEffect()).isEqualTo(Status.CONFUSED)
    assertThat(battleStats.getSpecialAttack()).isEqualTo(228)
    assertThat(battleStats.getSpecialDefense()).isEqualTo(120)
  }

  @Test
  fun `status gets cleared`(){
    val battleStats = TestData.waterMonster.getBattleStats()
    val buff = StatChange.Buff(
      attackSteps = 2,
      specialAttackSteps = 3
    )
    val clear = StatChange.Clear
    battleStats.applyChange(buff, TestData.waterMonster)
    battleStats.applyChange(clear, TestData.waterMonster)
    assertThat(battleStats.getHP()).isEqualTo(250)
    assertThat(battleStats.getInitiative()).isEqualTo(10)
    assertThat(battleStats.getAttack()).isEqualTo(100)
    assertThat(battleStats.getDefense()).isEqualTo(100)
    assertThat(battleStats.getStatusEffect()).isEqualTo(null)
    assertThat(battleStats.getSpecialAttack()).isEqualTo(120)
    assertThat(battleStats.getSpecialDefense()).isEqualTo(120)
  }
}