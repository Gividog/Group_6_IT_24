package hwr.oop.monsterleague

import monsterleague.gamelogic.BaseStats
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class BaseStatsTest : AnnotationSpec() {

    @Test
    fun `getInitiative returns correct value`() {
        val stats = BaseStats(
            healthPoints = 100,
            initiative = 75,
            attack = 50,
            defense = 40,
            specialDefense = 30,
            specialAttack = 20
        )

        assertThat(stats.getInitiative()).isEqualTo(75)
    }
}
