package monsterleague

import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec

class BattleTest : AnnotationSpec() {
    val dummyType1 = Type.GHOST
    val dummyType2 = Type.WATER

    private val dummyAttack = Attack(
        physicalAttack = PhysicalAttack("Punch", dummyType1, 100, 35,10)
    )

    private val dummyBuff = Buff(name = "Wut", effect = "keine Ahnung", type = dummyType1)
    private val dummyDebuff = Debuff(name = "Schwäche", effect = "keine Ahnung", type = dummyType2)

    private var dummyBaseStats = Stats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        buff = dummyBuff,
        debuff = dummyDebuff,
        statusEffect = 1
    )

    private var dummyBattleStats = Stats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        buff = dummyBuff,
        debuff = dummyDebuff,
        statusEffect = 1
    )

    private val dummyMonster1 = Monster(
        name = "Monster1",
        type = dummyType1,
        status = 1,
        BaseStats = dummyBaseStats,
        BattleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType2,
        status = 2,
        BaseStats = dummyBaseStats,
        BattleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val trainer1 = Trainer("trainer1", listOf(dummyMonster1, dummyMonster2), dummyMonster2, 3)
    private val trainer2 = Trainer("trainer2", listOf(dummyMonster1, dummyMonster2), dummyMonster1, 3)

    /**
     * Initial Values/Variables tests
     */

    /**
     * chooseAttack tests
     */

    @Test
    fun `attackingMonster, defendingMonster and attack are declared correctly`() {

        val attackingTrainer = trainer1
        val defendingTrainer = trainer2
        val battle = Battle(1,1,"",listOf(trainer1,trainer2))

       trainer1.chooseAttack(1)

        val attackingMonster = attackingTrainer.activeMonster
        val defendingMonster = defendingTrainer.activeMonster
        val chosenAttack = attackingMonster.attacks[0]

        assertThat(attackingMonster).isEqualTo(dummyMonster2)
        assertThat(defendingMonster).isEqualTo(dummyMonster1)
        assertThat(chosenAttack).isEqualTo(dummyAttack)
    }

    // TODO : AttackCalculator schreiben
    /*@Test
    fun `chooseAttack should set damageTrainer1 correctly`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )
        battle.chooseAttack(attackingTrainer = trainer1, defendingTrainer = trainer2, attackIndex = 1)
        assertThat(battle.damageTrainer1).isEqualTo(100)
        assertThat(battle.damageTrainer2).isEqualTo(0)
    }

    @Test
    fun `chooseAttack should set damageTrainer2 correctly`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )
        battle.chooseAttack(attackingTrainer = trainer2, defendingTrainer = trainer1, attackIndex = 1)
        val damage = 100
        assertThat(battle.damageTrainer2).isEqualTo(100)
        assertThat(battle.damageTrainer1).isEqualTo(0)
    }*/

    /**
     * surrender () tests
     */

    @Test
    fun `surrendering trainer causes opponent to win the battle`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )

        trainer1.surrender(battle)

        assertThat(battle.winner).isEqualTo("trainer2")
    }
}

