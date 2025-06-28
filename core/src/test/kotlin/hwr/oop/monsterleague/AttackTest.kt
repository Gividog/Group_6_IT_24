package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds
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

  @Test
  fun `Scratch has correct Data`() {
    val a = Attack.Scratch
    assertThat(a.name).isEqualTo("Scratch")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.NORMAL)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(40)
    assertThat(a.powerPoints).isEqualTo(35)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Tackle has correct Data`() {
    val a = Attack.Tackle
    assertThat(a.name).isEqualTo("Tackle")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.NORMAL)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(40)
    assertThat(a.powerPoints).isEqualTo(35)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Swift has correct Data`() {
    val a = Attack.Swift
    assertThat(a.name).isEqualTo("Swift")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.NORMAL)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(60)
    assertThat(a.powerPoints).isEqualTo(20)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Lick has correct Data`() {
    val a = Attack.Lick
    assertThat(a.name).isEqualTo("Lick")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.GHOST)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(30)
    assertThat(a.powerPoints).isEqualTo(30)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Burden has correct Data`() {
    val a = Attack.Burden
    assertThat(a.name).isEqualTo("Burden")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.GHOST)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(65)
    assertThat(a.powerPoints).isEqualTo(10)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Shadow Ball has correct Data`() {
    val a = Attack.ShadowBall
    assertThat(a.name).isEqualTo("Shadow Ball")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.GHOST)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(80)
    assertThat(a.powerPoints).isEqualTo(15)
    assertThat(a.isSpecial()).isTrue()
  }
}