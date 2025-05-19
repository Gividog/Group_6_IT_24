package monsterleague

import monsterleague.gamelogic.*

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.PhysicalAttack
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {
    private val dummyType = Type.WATER

    private val dummyAttack =  PhysicalAttack("Punch", dummyType, 100, 35, 10)
    

    private val dummyBuff = Buff(name = "Wut", effect = "keine Ahnung", type = dummyType)
    private val dummyDebuff = Debuff(name = "Schwäche", effect = "keine Ahnung", type = dummyType)

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
        type = dummyType,
        status = 1,
        BaseStats = dummyBaseStats,
        BattleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType,
        status = 2,
        BaseStats = dummyBaseStats,
        BattleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyMonster3 = Monster(
        name = "Monster3",
        type = dummyType,
        status = 3,
        BaseStats = dummyBaseStats,
        BattleStats = dummyBattleStats,
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

        dummyTrainer.switchActiveMonster(dummyMonster2)

        assertThat(dummyTrainer.activeMonster).isEqualTo(dummyMonster2)
    }

    @Test
    fun `the new selected active monster doesnt exist in Trainers monster list`(){
        val activeMonster = dummyTrainer.activeMonster
        dummyTrainer.switchActiveMonster(monster = dummyMonster3)
        val newSetActiveMonster = dummyTrainer.activeMonster

        assertThat(newSetActiveMonster).isEqualTo(activeMonster)
    }

    /**
     * healActiveMonster tests
     */

    @Test
    fun `healActiveMonster() heals monster's current hp (50) by 30 percent of its base hp (100) and reduces healsRemaining (0)`() {
        dummyTrainer.activeMonster.BattleStats.hp = 50
        dummyTrainer.healsRemaining = 1

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.activeMonster.BattleStats.hp).isEqualTo(80)
        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }

    @Test
    fun `healActiveMonster() does nothing when no heals remaining`() {
        dummyTrainer.healsRemaining = 0

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }
}