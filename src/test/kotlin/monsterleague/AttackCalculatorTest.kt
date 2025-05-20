package monsterleague

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.*
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.PhysicalAttack

class AttackCalculatorTest : AnnotationSpec() {

    private val dummyType = Type.WATER

    private val dummyAttack = PhysicalAttack("Punch", dummyType, 100, 35, 10)

    private val dummyBuff = Buff(name = "Wut", effect = "keine Ahnung", type = dummyType)

    private val dummyDebuff = Debuff(name = "Schwäche", effect = "keine Ahnung", type = dummyType)

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
        specialDefense = 40,
        buff = dummyBuff,
        debuff = dummyDebuff,
    )

    private val dummyMonster1 = Monster(
        name = "Monster1",
        type = Type.FIRE,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = Type.WATER,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyMonster3 = Monster(
        name = "Monster3",
        type = Type.GRASS,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster4 = Monster(
        name = "Monster4",
        type = Type.NORMAL,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )
    /*
    @Test
    fun`test calculateEfficiency returns 2,0 when attack is efficient`() {
        val attackingMonster = dummyMonster1
        val defendingMonster = dummyMonster3
        val efficiency = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateEfficiency()
        assertThat(efficiency).isEqualTo(2.0)
    }

    @Test
    fun`test calculateEfficiency returns 0,5 when attack is inefficient`() {
        val attackingMonster = dummyMonster1
        val defendingMonster = dummyMonster2
        val efficiency = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateEfficiency()
        assertThat(efficiency).isEqualTo(0.5)
    }

    @Test
    fun`test calculateEfficiency returns 1,0 when attack is neither efficient nor inefficient`() {
        val attackingMonster = dummyMonster4
        val defendingMonster = dummyMonster1
        val efficiency = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateEfficiency()
        assertThat(efficiency).isEqualTo(1.0)
    }


    @Test
    fun `criticalStrike is 2,0`(){
        val attackingMonster = dummyMonster4
        val defendingMonster = dummyMonster1
        val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateCriticalStrike{0.05}
        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun `criticalStrike is 1,0`(){
        val attackingMonster = dummyMonster4
        val defendingMonster = dummyMonster1
        val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateCriticalStrike{0.03}
        assertThat(result).isEqualTo(2.0)
    }

    @Test
    fun `criticalStrike is set to random`(){
        val attackingMonster = dummyMonster4
        val defendingMonster = dummyMonster1
        val result = DamageCalculator(attackingMonster, defendingMonster, dummyAttack, dummyBattleStats).calculateCriticalStrike()
        assertThat(result).isBetween(1.0,2.0)
    } */
}