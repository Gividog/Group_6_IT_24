package monsterleague

import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.mpp.log

class BattleTest : AnnotationSpec() {
    val dummyType1 = Type.GHOST
    val dummyType2 = Type.WATER

    private val dummyAttack = Attack(
        physicalAttack = PhysicalAttack("Punch", dummyType1, 100, 35, 10)
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
    private val trainer3 = Trainer("trainer2", listOf(dummyMonster1, dummyMonster2), dummyMonster1, 3)


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

    @Test
    fun `startNextRound should increment round and keep active monsters if alive`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )

        battle.startNextRound()

        assertThat(battle.round).isEqualTo(2)
        assertThat(battle.winner).isNull()
        assertThat(trainer1.activeMonster).isEqualTo(dummyMonster2)
        assertThat(trainer2.activeMonster).isEqualTo(dummyMonster1)
    }
    @Test
    fun `startNextRound should trigger surrender when all monsters fainted`() {
        trainer1.monsters.forEach { it.BattleStats.hp = 0 }
        trainer1.activeMonster.BattleStats.hp = 0

        val battle = Battle(
            battleID = 2,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )

        battle.startNextRound()

        assertThat(battle.winner).isEqualTo("trainer2")
        assertThat(battle.round).isEqualTo(1)
    }
    @Test
    fun `startNextRound should return immediately if winner exists`() {
        val battle = Battle(
            battleID = 3,
            round = 1,
            winner = "trainer2",
            trainers = listOf(trainer1, trainer2),
        )

        battle.startNextRound()
        assertThat(battle.round).isEqualTo(1)
        assertThat(battle.winner).isEqualTo("trainer2")
    }

    @Test
    fun `determineWinner() is declaring trainer1 as the winner if trainer3 has only dead monsters left`() {
        val battle = Battle(1, 1, null, listOf(trainer1, trainer3))
        val winner = battle.determineWinner(trainer1, trainer3)

        assertThat(trainer1).isEqualTo(winner)
    }

}

