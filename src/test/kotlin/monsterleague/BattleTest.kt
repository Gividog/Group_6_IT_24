package monsterleague

import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.PhysicalAttack
import java.util.*

class BattleTest : AnnotationSpec() {
    val dummyType1 = Type.GHOST
    val dummyType2 = Type.WATER

    private val dummyAttack =  PhysicalAttack("Punch", dummyType1, 100, 35, 10)

    private var dummyStatus = Status.CONFUSED

    private var dummyBaseStats = BaseStats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        specialAttack = 30,
        specialDefense = 40,
    )

    private var dummyBattleStats = BattleStats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        statusEffect = dummyStatus,
        specialAttack = 30,
        specialDefense = 40
    )

    private var dummyBattleStats2 = BattleStats(
        hp = 0,
        initiative = 10,
        attack = 20,
        defense = 30,
        statusEffect = null,
        specialAttack = 30,
        specialDefense = 40
    )

    private val dummyMonster1 = Monster(
        name = "Monster1",
        type = dummyType1,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType2,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyMonster3 = Monster(
        name = "Monster3",
        type = dummyType1,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats2,
        attacks = listOf(dummyAttack)
    )

    val uuid = UUID.randomUUID()

    private val dummyTrainer1 = Trainer("trainer1", listOf(dummyMonster1, dummyMonster2), dummyMonster2,3)
    private val dummyTrainer2 = Trainer("trainer2", listOf(dummyMonster1, dummyMonster3), dummyMonster1, 3)
    private val dummyTrainer3 = Trainer("trainer3", listOf(dummyMonster3), dummyMonster3, 0)

    /**
     * surrender () tests
     */

    @Test
    fun `surrendering trainer causes opponent to win the battle`() { // Test erledigt
        val battle = Battle(uuid,listOf(dummyTrainer1,dummyTrainer2))

        battle.surrender(dummyTrainer1)

        assertThat(battle.getWinner()).isEqualTo(dummyTrainer2)
    }

    /**
     * startNextRound() tests
     */

    @Test
    fun `startNextRound should increment round and keep active monsters if alive`() { // Test an Funktion anpassen?
        val battle = Battle(
            battleID = uuid,
            trainers = listOf(dummyTrainer1, dummyTrainer2),
        )

        battle.startNextRound()

        assertThat(battle.getRounds()).isEqualTo(2)
        assertThat(battle.getWinner()).isNull()
        assertThat(dummyTrainer1.activeMonster).isEqualTo(dummyMonster2)
        assertThat(dummyTrainer2.activeMonster).isEqualTo(dummyMonster1)
    }

    /**
     * determineWinner() tests
     */

    @Test
    fun `determineWinner() returns null when both trainers don't have any dead monsters`() {
        val battle = Battle(
            battleID = uuid,
            trainers = listOf(dummyTrainer1, dummyTrainer2),
        )

        battle.determineWinner()

        assertThat(battle.getWinner()).isEqualTo(null)
        assertThat(battle.getWinner()).isNotEqualTo(dummyTrainer1)
        assertThat(battle.getWinner()).isNotEqualTo(dummyTrainer2)
    }

    @Test
    fun `determineWinner() is declaring the first trainer in the list as the winner if the second trainer has no alive monsters left`() { // Test an neue Funktion anpassen
        val battle = Battle(
            battleID = uuid,
            trainers = listOf(dummyTrainer1, dummyTrainer3),
        )

        battle.determineWinner()

        assertThat(battle.getWinner()).isEqualTo(dummyTrainer1)
        assertThat(battle.getWinner()).isNotEqualTo(dummyTrainer3)
    }

    @Test
    fun `second Trainer Is Winner`() { // Test an neue Funktion anpassen
        val battle = Battle(
            battleID = uuid,
            trainers = listOf(dummyTrainer3,dummyTrainer1),
        )

        battle.determineWinner()

        assertThat(battle.getWinner()).isEqualTo(dummyTrainer1)
        assertThat(battle.getWinner()).isNotEqualTo(dummyTrainer3)
    }



    @Test
    fun `simulateRound()`() {
        // TODO
    }

    @Test
    fun `getWinner()`() {
        // TODO
    }

}

