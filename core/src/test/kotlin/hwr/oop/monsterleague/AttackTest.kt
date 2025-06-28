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
  fun `vine whip`(){
    val a = Attack.VineWhip
    assertThat(a.name).isEqualTo("Vine Whip")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.GRASS)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(45)
    assertThat(a.powerPoints).isEqualTo(25)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Razor Leaf`(){
    val a = Attack.RazorLeaf
    assertThat(a.name).isEqualTo("Razor Leaf")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.GRASS)
    assertThat(a.accuracy).isEqualTo(95)
    assertThat(a.power).isEqualTo(55)
    assertThat(a.powerPoints).isEqualTo(25)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Solar Beam`(){
    val a = Attack.SolarBeam
    assertThat(a.name).isEqualTo("Solar Beam")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.GRASS)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(120)
    assertThat(a.powerPoints).isEqualTo(10)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Fire Fang`(){
    val a = Attack.FireFang
    assertThat(a.name).isEqualTo("Fire Fang")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.FIRE)
    assertThat(a.accuracy).isEqualTo(95)
    assertThat(a.power).isEqualTo(65)
    assertThat(a.powerPoints).isEqualTo(15)
    assertThat(a.isSpecial()).isFalse()
  }

  @Test
  fun `Ember`(){
    val a = Attack.Ember
    assertThat(a.name).isEqualTo("Ember")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.FIRE)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(40)
    assertThat(a.powerPoints).isEqualTo(25)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Flame Thrower`(){
    val a = Attack.Flamethrower
    assertThat(a.name).isEqualTo("Flamethrower")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.FIRE)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(90)
    assertThat(a.powerPoints).isEqualTo(15)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Water gun`(){
    val a = Attack.WaterGun
    assertThat(a.name).isEqualTo("Water Gun")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.WATER)
    assertThat(a.accuracy).isEqualTo(100)
    assertThat(a.power).isEqualTo(40)
    assertThat(a.powerPoints).isEqualTo(25)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `Hydro Pump`(){
    val a = Attack.HydroPump
    assertThat(a.name).isEqualTo("Hydro Pump")
    assertThat(a.kind).isEqualTo(AttackKinds.SPECIAL)
    assertThat(a.type).isEqualTo(Type.WATER)
    assertThat(a.accuracy).isEqualTo(85)
    assertThat(a.power).isEqualTo(110)
    assertThat(a.powerPoints).isEqualTo(5)
    assertThat(a.isSpecial()).isTrue()
  }

  @Test
  fun `AquaTail`(){
    val a = Attack.AquaTail
    assertThat(a.name).isEqualTo("Aqua Tail")
    assertThat(a.kind).isEqualTo(AttackKinds.PHYSICAL)
    assertThat(a.type).isEqualTo(Type.WATER)
    assertThat(a.accuracy).isEqualTo(90)
    assertThat(a.power).isEqualTo(90)
    assertThat(a.powerPoints).isEqualTo(10)
    assertThat(a.isSpecial()).isFalse()
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