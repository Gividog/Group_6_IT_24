package monsterleague

import hwr.oop.monsterleague.TestData
import hwr.oop.monsterleague.gamelogic.TrainerChoice
import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.AttackKinds
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.assertThrows
import java.util.*

class BattleTest : AnnotationSpec() {
  /**
   * surrender () tests
   */

  @Test
  fun `surrendering trainer causes opponent to win the battle`() {
    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithTwoMonsters,
      true

    )

    battle.surrender(TestData.trainerWithTwoMonsters)

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
  }

  /**
   * startNextRound() tests
   */

  @Test
  fun `startNextRound should increment round and keep active monsters if alive`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithOneDefeatedMonster,
      true,
    )

    battle.startNextRound()

    assertThat(battle.getRounds()).isEqualTo(2)
    assertThat(battle.getWinner()).isNull()
    assertThat(
      battle.getTrainerOne().getActiveMonster()
    ).isEqualTo(TestData.fireMonster)
    assertThat(
      battle.getTrainerTwo().getActiveMonster()
    ).isEqualTo(TestData.waterMonster)
  }

  /**
   * determineWinner() tests
   */

  @Test
  fun `determineWinner() returns null when both trainers don't have any dead monsters`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithOneDefeatedMonster,
      true
    )

    battle.determineWinner()

    assertThat(battle.getWinner()).isEqualTo(null)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithTwoMonsters)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOneDefeatedMonster)
  }

  @Test
  fun `determineWinner() is declaring the first trainer in the list as the winner if the second trainer has no alive monsters left`() { // Test an neue Funktion anpassen
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithOnlyDefeatedMonsters,
      true
    )

    battle.determineWinner()

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
  }

  @Test
  fun `second Trainer Is Winner`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true
    )

    battle.determineWinner()

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
  }

  @Test
  fun `simulateRound()`() {
    // TODO
  }

  @Test
  fun `getWinner()`() {
    // TODO
  }

  @Test
  fun `active Monsters are sorted descending`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithGhostMonsterLeft,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true
    )

    val descendingSortedList = battle.sortActiveMonstersByInitiative()
    assertThat(descendingSortedList).containsExactly(
      TestData.fireMonster,
      TestData.ghostMonster
    )
  }

  @Test
  fun `kind of Attack is Physical`() {
    val kind =
      Battle(
        TestData.battleUuid,
        TestData.trainerWithTwoMonsters,
        TestData.trainerWithOnlyDefeatedMonsters,
        true,
      ).getKindOfAttack(TestData.physicalAttackTackle)
    assertThat(kind).isEqualTo(AttackKinds.PHYSICAL)
  }

  @Test
  fun `next active Monster is fireMonster`() {
    Battle(
      TestData.battleUuid,
      trainerOne = TestData.trainerWithOneDefeatedMonster,
      trainerTwo = TestData.trainerWithOneDefeatedMonster,
      true,
    ).endRound()

    assertThat(TestData.trainerWithOneDefeatedMonster.getActiveMonster()).isEqualTo(
      TestData.waterMonster
    )
    assertThat(TestData.trainerWithOneDefeatedMonster.getMonsters()).isNotNull()

  }

  @Test
  fun `no monsters left to replace active monsters`() {
    Battle(
      TestData.battleUuid,
      TestData.trainerWithOnlyDefeatedMonsters,
      TestData.trainerWithOneDefeatedMonster,
      true,
    ).endRound()

    assertThat(TestData.trainerWithOnlyDefeatedMonsters.getActiveMonster()).isEqualTo(
      TestData.defeatedMonster
    )
  }

  /**
   * chooseAttack tests
   */

  @Test
  fun `chosen Attack is not available`() {
    try {
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = TestData.trainerWithTwoMonsters,
        trainerTwo = TestData.trainerWithTwoMonsters,
        true,
      )
      val trainer = TestData.trainerWithTwoMonsters
      val attackChoice = TrainerChoice.AttackChoice(
        attackingMonster = trainer.getActiveMonster(),
        selectedAttack = TestData.physicalAttackSplash,
        targetedMonster = TestData.trainerWithOneDefeatedMonster.getActiveMonster()

      )
      battle.trainerChooseAttack(trainer, attackChoice)
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * changeActiveMonster tests
   */

  @Test
  fun `the newly selected monster at index 1 (so the second element) gets set as the trainers activeMonster`() {
    val battle = Battle(
      TestData.battleUuid, TestData.trainerWithTwoMonsters,
      TestData.trainerWithOneDefeatedMonster,
      true
    )
    TestData.trainerWithTwoMonsters.setMonsters(
      listOf(
        TestData.waterMonster,
        TestData.fireMonster
      )
    )
    TestData.trainerWithTwoMonsters.setActiveMonster(TestData.waterMonster)
    val switchChoice = TrainerChoice.SwitchChoice(
      outMonster = TestData.waterMonster,
      inMonster = TestData.fireMonster
    )
    battle.switchActiveMonster(TestData.trainerWithTwoMonsters, switchChoice)

    assertThat(TestData.trainerWithTwoMonsters.getActiveMonster()).isEqualTo(
      TestData.fireMonster
    )
  }

  @Test
  fun `the new selected active monster doesnt exist in Trainers monster list`() {
    val battle = Battle(
      TestData.battleUuid, TestData.trainerWithTwoMonsters,
      TestData.trainerWithOneDefeatedMonster,
      true,
    )

    val activeMonster = TestData.trainerWithTwoMonsters.getActiveMonster()
    val switchChoice = TrainerChoice.SwitchChoice(
      outMonster = activeMonster,
      inMonster = TestData.ghostMonster
    )
    battle.switchActiveMonster(TestData.trainerWithTwoMonsters, switchChoice)
    val newSetActiveMonster = TestData.trainerWithTwoMonsters.getActiveMonster()

    assertThat(newSetActiveMonster).isEqualTo(activeMonster)
  }

  /**
   * healActiveMonster tests
   */

  @Test
  fun `healActiveMonster() heals monster's current hp (50) by 30 percent of its base hp (100) and reduces healsRemaining (0)`() {

    val battle = Battle(
      TestData.battleUuid, TestData.trainerWithTwoMonsters,
      TestData.trainerWithOneDefeatedMonster,
      true,
    )

    TestData.trainerWithTwoMonsters.setHealsRemaining(1)

    val choice =
      TrainerChoice.HealChoice(TestData.trainerWithTwoMonsters.getActiveMonster())
    battle.healActiveMonster(TestData.trainerWithTwoMonsters, choice)

    assertThat(TestData.trainerWithTwoMonsters.getActiveMonstersHP()).isEqualTo(
      225
    )
    assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
  }

  @Test
  fun `healActiveMonster() does nothing when no heals remaining`() {
    val trainer = TestData.trainerWithTwoMonsters

    trainer.setHealsRemaining(0)

    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerTwo = TestData.trainerWithOnlyDefeatedMonsters,
      trainerOne = TestData.trainerWithTwoMonsters,
      simpleDamageCalculation = true
    )

    val choice =
      TrainerChoice.HealChoice(TestData.trainerWithTwoMonsters.getActiveMonster())

    battle.healActiveMonster(trainer, choice)

    assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
  }

  @Test
  fun `trainer3 chose physicalAttackTackle`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true
    )
    val trainer = TestData.trainerWithTwoMonsters
    val targetTrainer = TestData.trainerWithOneDefeatedMonster
    val attackChoice = TrainerChoice.AttackChoice(
      attackingMonster = trainer.getActiveMonster(),
      selectedAttack = TestData.physicalAttackTackle,
      targetedMonster = TestData.trainerWithOneDefeatedMonster.getActiveMonster()

    )
    val initialHP = targetTrainer.getActiveMonster().getHP()
    battle.trainerChooseAttack(trainer, attackChoice)

    val afterHP = targetTrainer.getActiveMonster().getHP()

    assertThat(afterHP).isLessThan(initialHP)
  }

  /* @Test
   fun `simulateRound applies damage in correct initiative order`() {
     val battle = Battle(
       battleID = TestData.battleUuid,
       trainerOne = TestData.trainerWithTwoMonsters,
       trainerTwo = TestData.trainerWithOneDefeatedMonster,
       true
     )
     val startingHP1 = TestData.trainerWithTwoMonsters.getActiveMonstersHP()
     val startingHP2 = TestData.trainerWithOneDefeatedMonster.getActiveMonstersHP()

     battle.chooseAttack(TestData.trainerWithTwoMonsters, TestData.physicalAttackTackle)
     battle.chooseAttack(TestData.trainerWithOneDefeatedMonster, TestData.physicalAttackTackle)

     assertThat(TestData.trainerWithTwoMonsters.getActiveMonstersHP()).isLessThan(startingHP1)
     assertThat(TestData.trainerWithOneDefeatedMonster.getActiveMonstersHP()).isLessThan(startingHP2)

     assertThat(battle.getChosenAttackMap()).isEmpty()
   }*/

  @Test
  fun `status effect gets changed from none to confused `() {
    val battleStats = TestData.battleStatsWithoutStatus
    battleStats.updateStatusEffect(Status.CONFUSED)
    val nameOfStatus = battleStats.getStatusEffect()

    assertThat(nameOfStatus).isEqualTo(Status.CONFUSED)
  }

  /*
      SimulateRound Tets
  */
  @Test
  fun `simulateRound with two attacks applies both and cleas choices`() {
    val trainer1 = TestData.trainerWithTwoMonsters
    val trainer2 = TestData.trainerWithOneDefeatedMonster
    val battle = Battle(TestData.battleUuid, trainer1, trainer2, simpleDamageCalculation = true)

    val monster1 = trainer1.getActiveMonster()
    val monster2 = trainer2.getActiveMonster()
    val before1 = monster1.getHP()
    val before2 = monster2.getHP()

    val mapField = battle.javaClass.getDeclaredField("mapOfChoice").apply { isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    val choices = mapField.get(battle) as MutableMap<TrainerInBattle, TrainerChoice>
    choices[trainer1] = TrainerChoice.AttackChoice(monster1, TestData.physicalAttackTackle, monster2)
    choices[trainer2] = TrainerChoice.AttackChoice(monster2, TestData.physicalAttackSplash, monster1)

    val statsField = battle.javaClass.getDeclaredField("battleStats").apply { isAccessible = true }
    statsField.set(battle, mutableMapOf(monster1 to monster1.getBattleStats(), monster2 to monster2.getBattleStats()))

    battle.javaClass.getDeclaredMethod("simulateRound").apply { isAccessible = true }.invoke(battle)

    assertThat(monster1.getHP()).isLessThan(before1)
    assertThat(monster2.getHP()).isLessThan(before2)
    assertThat(choices).isEmpty()
  }

  @Test
  fun `simulateRound with switch and attack switches then attacks and clears choices`() {
    val switcher = TrainerInBattle(
      name = "switcher",
      monsters = listOf(TestData.waterMonster, TestData.fireMonster),
      activeMonster = TestData.waterMonster,
      healsRemaining = 0
    )
    val attacker = TrainerInBattle(
      name = "attacker",
      monsters = listOf(TestData.ghostMonster),
      activeMonster = TestData.ghostMonster,
      healsRemaining = 0
    )
    val battle = Battle(TestData.battleUuid, switcher, attacker, simpleDamageCalculation = true)

    val beforeSwitch = switcher.getActiveMonster()
    val target       = switcher.getActiveMonster()
    val beforeHP     = target.getHP()

    val mapField = battle.javaClass.getDeclaredField("mapOfChoice").apply { isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    val choices = mapField.get(battle) as MutableMap<TrainerInBattle, TrainerChoice>
    choices[switcher] = TrainerChoice.SwitchChoice(outMonster = beforeSwitch, inMonster = TestData.fireMonster)
    choices[attacker] = TrainerChoice.AttackChoice(attacker.getActiveMonster(), TestData.physicalAttackTackle, switcher.getActiveMonster())

    val statsField = battle.javaClass.getDeclaredField("battleStats").apply { isAccessible = true }
    statsField.set(battle, mutableMapOf(
      beforeSwitch to beforeSwitch.getBattleStats(),
      TestData.fireMonster to TestData.fireMonster.getBattleStats(),
      attacker.getActiveMonster() to attacker.getActiveMonster().getBattleStats()
    ))

    battle.javaClass.getDeclaredMethod("simulateRound").apply { isAccessible = true }.invoke(battle)

    assertThat(switcher.getActiveMonster()).isEqualTo(TestData.fireMonster)
    assertThat(switcher.getActiveMonster().getHP()).isLessThan(beforeHP)
    assertThat(choices).isEmpty()
  }

  @Test
  fun `simulateRound with heal applies healing and clears choices`() {
    // given: a trainer with exactly one heal available and a damaged monster
    val healer = TrainerInBattle(
      name           = "healer",
      monsters       = listOf(TestData.waterMonster),
      activeMonster  = TestData.waterMonster,
      healsRemaining = 1
    )
    val battle = Battle(
      battleID                = TestData.battleUuid,
      trainerOne              = healer,
      trainerTwo              = TestData.trainerWithTwoMonsters, // dummy second trainer
      simpleDamageCalculation = true
    )

    val monster = healer.getActiveMonster()
    monster.takeDamage(80)
    val hpBefore   = monster.getHP()
    val healsBefore = healer.getHealsRemaining()

    val mapField = battle.javaClass
      .getDeclaredField("mapOfChoice")
      .apply { isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    val choices = mapField.get(battle) as MutableMap<TrainerInBattle, TrainerChoice>
    choices[healer] = TrainerChoice.HealChoice(monster)

    battle.javaClass
      .getDeclaredMethod("simulateRound")
      .apply { isAccessible = true }
      .invoke(battle)

    assertThat(monster.getHP()).isGreaterThan(hpBefore)
    assertThat(healer.getHealsRemaining()).isEqualTo(healsBefore - 1)
    assertThat(choices).isEmpty()
  }

  /*
        handleAttack Tests
  */

  @Test
  fun `trainerChooseAttack throws IllegalArgumentException when stats missing`() {
    // given: a fresh Battle with empty battleStats
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      simpleDamageCalculation = true
    )

    val attacker = TestData.trainerWithTwoMonsters.getActiveMonster()
    val defender = TestData.trainerWithTwoMonsters.getActiveMonster()
    val choice = TrainerChoice.AttackChoice(
      attackingMonster = attacker,
      selectedAttack   = TestData.physicalAttackTackle,
      targetedMonster  = defender
    )

    val exception = assertThrows<IllegalArgumentException> {
      battle.trainerChooseAttack(TestData.trainerWithTwoMonsters, choice)
    }
    assertThat(exception).hasMessageContaining("Attacker stats not found")
  }

  @Test
  fun `trainerChooseAttack throws IllegalArgumentException when defender stats missing`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithOneDefeatedMonster,
      simpleDamageCalculation = true
    )

    val attacker = TestData.trainerWithTwoMonsters.getActiveMonster()           // fireMonster
    val defender = TestData.trainerWithOneDefeatedMonster.getActiveMonster()    // defeatedMonster

    val choice = TrainerChoice.AttackChoice(
      attackingMonster = attacker,
      selectedAttack   = TestData.physicalAttackTackle,
      targetedMonster  = defender
    )

    val statsField = battle.javaClass
      .getDeclaredField("battleStats")
      .apply { isAccessible = true }
    statsField.set(battle, mutableMapOf(attacker to attacker.getBattleStats()))

    val ex = assertThrows<IllegalArgumentException> {
      battle.trainerChooseAttack(
        TestData.trainerWithTwoMonsters,  // the trainer who is doing the attack
        choice
      )
    }
    assertThat(ex).hasMessageContaining("Defender stats not found")
  }
}

