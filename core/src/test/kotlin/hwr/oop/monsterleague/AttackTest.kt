package hwr.oop.monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.DragonDance
import monsterleague.gamelogic.attacks.Haze
import monsterleague.gamelogic.attacks.Screech
import monsterleague.gamelogic.attacks.SwordsDance
import org.assertj.core.api.Assertions.assertThat

class AttackTest : AnnotationSpec() {

  @Test
  fun `Swords Dance increases attacker's attack stat by 2 steps and Haze resets it`() {
    val monster = TestData.normalMonster
    val battleStats = monster.getBattleStats()
    val buff = SwordsDance

    battleStats.applyChange(buff.attackerStatusChange(), monster)

    assertThat(monster.getBattleStats().getAttack()).isEqualTo(160)
  }

  @Test
  fun `Dragon Dance increases attacker's initiative and attack stat by one step each`() {
    val monster = TestData.normalMonster
    val battleStats = monster.getBattleStats()
    val buff = DragonDance

    battleStats.applyChange(buff.attackerStatusChange(), monster)

    assertThat(monster.getBattleStats().getAttack()).isEqualTo(130)
    assertThat(monster.getBattleStats().getInitiative()).isEqualTo(13)
  }

  @Test
  fun `Screech lowers defense of defender by 2 steps`() {
    val monster = TestData.normalMonster
    val battleStats = monster.getBattleStats()
    val buff = Screech

    battleStats.applyChange(buff.defenderStatusChange(), monster)

    assertThat(monster.getBattleStats().getDefense()).isEqualTo(40)
  }
}