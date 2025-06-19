package hwr.oop.monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.DebuffAttack
import org.assertj.core.api.Assertions.assertThat

class AttackTest : AnnotationSpec() {
  @Test
  fun `useDebuff multiplies the chosen stat by 0,3 and reduces it by the outcoming value` () { // DebuffAttack Test
    val debuff = DebuffAttack("Attack Debuff", AttackKinds.DEBUFF, Type.NORMAL, 100, 0, 3)
    var exampleStat = TestData.battleStatsHigherAttack.getAttack()

    debuff.useDebuff(exampleStat)
    assertThat(exampleStat).isEqualTo(56)
  }
}