package hwr.oop.classes

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FightTest : AnnotationSpec() {

    private val dummyAttackData = AttackSpecificData("Punch", 40, 100, 35)
    private val dummyAttack = Attack(
        type = dummyAttackData,
        category = Category.PHYSICAL,
        attackSpecificData = dummyAttackData
    )

    private val dummyStats = Stats(
        hp = 100,
        currenthp = 100,
        initiative = 10,
        attack = 10,
        defense = 10,
        specialAttack = 10,
        specialDefense = 10
    )

    private val monster1 = Monster(
        name = "Monster1",
        type = Type.NORMAL,
        stats = dummyStats,
        attacks = listOf(dummyAttack)
    )

    private val monster2 = Monster(
        name = "Monster2",
        type = Type.NORMAL,
        stats = dummyStats,
        attacks = listOf(dummyAttack)
    )

    private val trainer1 = Trainer("Ash", mutableListOf(monster1), activeMonster = monster1)
    private val trainer2 = Trainer("Gary", mutableListOf(monster2), activeMonster = monster2)

    @Test
    fun `startFight sets fightStatus to true`() {
        val fight = Fight(1, false, listOf(trainer1, trainer2))

        fight.startFight()

        assertThat(fight.fightStatus).isTrue()
    }

    @Test
    fun `chooseAttack with valid index prints attack`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))

        fight.chooseAttack(1)

        // No exception thrown is enough for now
    }
/* Will be done when calculateAttack() gets implemented
    @Test
    fun `chooseAttack chooses an attack by index and makes the monster attack with that attack`() {
    }
*/
    @Test
    fun `healMonster heals monster and reduces healsRemaining`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))
        trainer1.activeMonster!!.stats.currenthp = 50 // !! because it can be NULL when all monsters of a trainer are dead
        trainer1.healsRemaining = 1

        fight.healMonster()

        assertThat(trainer1.activeMonster!!.stats.currenthp).isEqualTo(80)
        assertThat(trainer1.healsRemaining).isEqualTo(0)
    }

    @Test
    fun `healMonster does nothing when no heals remaining`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))
        trainer1.healsRemaining = 0

        fight.healMonster()

        assertThat(trainer1.healsRemaining).isEqualTo(0)
    }

    /* TODO find the issue why the monster stays monster1 (could be because of the call of endTurn() but not sure yet)
    @Test
    fun `changeMonster switches activeMonster`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))

        fight.changeMonster(2) // 2 weil wir den Index der Liste manuell bei 1 starten lassen statt bei 0

        assertThat(trainer1.activeMonster?.name).isEqualTo("Monster2")
    }
    */

    @Test
    fun `changeMonster with invalid index does not crash`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))

        fight.changeMonster(5)

        assertThat(trainer1.activeMonster?.name).isEqualTo("Monster1")
    }

    @Test
    fun `fightOver returns true when a trainer has no monsters left`() {
        val deadMonster = Monster(
            name = "DeadMonster",
            type = Type.NORMAL,
            stats = dummyStats.copy(currenthp = 0),
            attacks = listOf(dummyAttack)
        )
        val faintedTrainer = Trainer("Fainted", mutableListOf(deadMonster), activeMonster = deadMonster)
        val fight = Fight(1, true, listOf(trainer1, faintedTrainer))

        assertThat(fight.fightOver()).isTrue()
    }

    @Test
    fun `determineWinner returns trainer with surviving monster`() {
        val deadMonster = Monster(
            name = "DeadMonster",
            type = Type.NORMAL,
            stats = dummyStats.copy(currenthp = 0),
            attacks = listOf(dummyAttack)
        )
        val faintedTrainer = Trainer("Fainted", mutableListOf(deadMonster), activeMonster = deadMonster)
        val fight = Fight(1, true, listOf(trainer1, faintedTrainer))

        assertThat(fight.determineWinner()).isEqualTo("Ash")
    }

    @Test
    fun `endFight sets fightStatus to false and sets winner`() {
        val fight = Fight(1, true, listOf(trainer1, trainer2))

        fight.endFight("Ash")

        assertThat(fight.fightStatus).isFalse()
        assertThat(fight.winner).isEqualTo("Ash")
    }
}
