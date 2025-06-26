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
    val monster = TestData.buffMonsterSwordDancer
    val battleStats = monster.getBattleStats()
    val buff = SwordsDance

    battleStats.applyChange(buff.attackerStatChange(), monster)
    assertThat(monster.getBattleStats().getAttack()).isEqualTo(160)

    val deBuff = Haze
    battleStats.applyChange(deBuff.attackerStatChange(), monster)
    assertThat(monster.getBattleStats().getAttack()).isEqualTo(100)
  }

  @Test
  fun `Dragon Dance increases attacker's initiative and attack stat by one step each`() {
    val monster = TestData.buffMonsterDragonDancer
    val battleStats = monster.getBattleStats()
    val buff = DragonDance

    battleStats.applyChange(buff.attackerStatChange(), monster)

    assertThat(monster.getBattleStats().getAttack()).isEqualTo(130)
    assertThat(monster.getBattleStats().getInitiative()).isEqualTo(39)
  }

  @Test
  fun `Screech lowers defense of defender by 2 steps`() {
    val monster = TestData.buffMonsterScreech
    val battleStats = monster.getBattleStats()
    val buff = Screech

    battleStats.applyChange(buff.defenderStatChange(), monster)

    assertThat(monster.getBattleStats().getDefense()).isEqualTo(36)
  }
}