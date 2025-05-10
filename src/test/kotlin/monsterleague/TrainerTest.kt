package monsterleague

import monsterleague.gamelogic.Attack
import monsterleague.gamelogic.PhysicalAttack
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Trainer

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {
    val dummyType = Type.WATER

    private val dummyBaseStats = BaseStats(
        hp = 100,
        initiative = 10,
        buff = 5,
        debuff = 10
    )

    private val dummyBattleStats = BattleStats(
        currenthp = 50,
        statusEffect = 2,
        buffActive = false,
        debuffActive = false
    )

    private val dummyAttack = Attack(
        physicalAttack = PhysicalAttack("Punch", dummyType, 100, 35,10)
    )

    private val dummyMonster1 = Monster(
        name = "Monster1",
        type = dummyType,
        status = 1,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType,
        status = 2,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyTrainer = Trainer("trainer1", listOf(dummyMonster1, dummyMonster2), dummyMonster1, 3)

    /**
     * chooseAttack tests
     */

    @Test
    fun `chosen Attack is Punch`(){
        val attack = dummyTrainer.chooseAttack(1)
        assertThat(attack).isEqualTo(dummyAttack)
    }

    /**
     * changeActiveMonster tests
     */

    @Test
    fun `the newly selected monster at index 1 (so the second element) gets set as the trainers activeMonster`() {
        dummyTrainer.monsters = listOf(dummyMonster1, dummyMonster2)
        dummyTrainer.activeMonster = dummyMonster1

        val index = 2

        dummyTrainer.switchActiveMonster(index)
        val newActiveMonster = dummyTrainer.monsters[index - 1]

        assertThat(newActiveMonster).isEqualTo(dummyMonster2)
    }

    @Test
    fun `the new selected active monster doesnt exist in Trainers monster list`(){
        val activeMonster = dummyTrainer.activeMonster
        dummyTrainer.switchActiveMonster(4)
        val newSetActiveMonster = dummyTrainer.activeMonster

        assertThat(newSetActiveMonster).isEqualTo(activeMonster)
    }

    /**
     * healActiveMonster tests
     */

    @Test
    fun `healActiveMonster() heals monster's currenthp (80) and reduces healsRemaining (0)`() {
        dummyTrainer.activeMonster.battleStats.currenthp = 50
        dummyTrainer.healsRemaining = 1

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.activeMonster.battleStats.currenthp).isEqualTo(80)
        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }

    @Test
    fun `healActiveMonster() does nothing when no heals remaining`() {
        dummyTrainer.healsRemaining = 0

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }
}