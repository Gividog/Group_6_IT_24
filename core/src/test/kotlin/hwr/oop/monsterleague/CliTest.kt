package hwr.oop.monsterleague

import hwr.oop.monsterleague.cli.BattleHolder
import hwr.oop.monsterleague.cli.ChooseActionCommand
import hwr.oop.monsterleague.cli.CreateBattleCommand
import hwr.oop.monsterleague.cli.ChooseAttackCommand
import hwr.oop.monsterleague.gamelogic.trainers.Trainer
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.BaseStats
import hwr.oop.monsterleague.gamelogic.Battle
import hwr.oop.monsterleague.gamelogic.BattleStats
import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

class CliTest : AnnotationSpec() {

  /**
   * Setup of TrainerFactory before and after every test
   */

  private val originalTrainers = TrainerFactory.getAll()

  @BeforeEach
  fun setupTrainerFactory() {
    TrainerFactory.clear()
    val monsters = listOf(
      Monster(
        name = "Bulbasaur",
        type = Type.GRASS,
        baseStats = BaseStats(
          healthPoints = 35,
          attack = 55,
          defense = 40,
          specialAttack = 65,
          specialDefense = 65,
          initiative = 45
        ),
        battleStats = BattleStats(
          healthPoints = 35,
          attack = 55,
          defense = 40,
          specialAttack = 65,
          specialDefense = 65,
          initiative = 45,
          statusEffect = null
        ),
        attacks = listOf(Attack.VineWhip, Attack.RazorLeaf, Attack.SolarBeam)
      )
    )
    TrainerFactory.save(Trainer("Ash", monsters))
    TrainerFactory.save(Trainer("Misty", monsters))
  }

  @AfterEach
  fun resetTrainerFactory() {
    TrainerFactory.clear()
    originalTrainers.values.forEach { trainer ->
      TrainerFactory.save(trainer)
    }
  }

  /**
   * CreateBattleCommand Tests
   */

  @Test
  fun `should create a new battle with given trainers`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Misty,0")

    val output = captureStandardOut {
      command.handle(args)
    }.trim()

    assertThat(output).contains("Battle created with ID")
  }

  @Test
  fun `should fail when same trainer is used twice`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Ash,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("Cannot create a Battle with the same trainer twice")
  }

  @Test
  fun `should fail if trainerOne not found`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Brock,Misty,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("Cannot find trainer Brock")
  }

  @Test
  fun `should fail if trainerTwo not found`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Brock,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("Cannot find trainer Brock")
  }

  @Test
  fun `should fail if expected format wasn't matched`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("Expected format: --trainers=Ash,Misty,0")
  }

  /**
   * matches() Tests (CreateBattleCommand)
   */

  @Test
  fun `matches returns true for correct format`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Misty,0")

    val result = command.matches(args)

    assertThat(result).isTrue()
  }

  @Test
  fun `matches returns false if command is wrong`() {
    val command = CreateBattleCommand()
    val args = listOf("game", "new", "--trainers=Ash,Misty,0")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  @Test
  fun `matches returns false if trainers option missing`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  @Test
  fun `matches returns false if --trainers= is empty`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  /**
   * ChooseAttackCommand Tests
   */

  private lateinit var command: ChooseAttackCommand

  @BeforeEach
  fun setupBattle() {
    BattleHolder.currentBattle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true
    )
    command = ChooseAttackCommand()
  }

  @AfterEach
  fun teardown() {
    BattleHolder.currentBattle = null
  }

  @Test
  fun `handle registers attack choice correctly`() {
    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true
    )
    BattleHolder.currentBattle = battle

    val args = listOf(
      "battle", "attack",
      "--trainer=trainer1",
      "--attacker=Monster2",
      "--attack=Tackle",
      "--target=Monster3"
    )

    command.handle(args)

    val registeredChoice =
      battle.getSubmittedChoice(TestData.trainerWithTwoMonsters)
    assertThat(registeredChoice).isInstanceOf(TrainerChoice.AttackChoice::class.java)

    val attackChoice = registeredChoice as TrainerChoice.AttackChoice
    assertThat(attackChoice.attackingMonster.getName()).isEqualTo("Monster2")
    assertThat(attackChoice.selectedAttack.name).isEqualTo("Tackle")
    assertThat(attackChoice.targetedMonster.getName()).isEqualTo("Monster3")
  }

  @Test
  fun `handle throws exception if less than 6 args`() {
    val args =
      listOf("battle", "attack", "--trainer=Ash", "--attacker=Balbasaur")
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Missing parameters for attack command")
  }

  @Test
  fun `handle throws exception if trainer not found`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=NonExistentTrainer",
      "--attacker=Monster1",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("No trainer with name: NonExistentTrainer")
  }

  @Test
  fun `handle throws exception if attacker monster not found`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=trainer1",
      "--attacker=NonExistentMonster",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("No monster with name 'NonExistentMonster' for trainer 'trainer1'")
  }

  @Test
  fun `handle throws exception if attack not found`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=trainer1",
      "--attacker=Monster1",
      "--attack=NonExistentAttack",
      "--target=Monster2"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("No attack with name 'NonExistentAttack' on monster 'Monster1'")
  }

  @Test
  fun `handle throws exception if target monster not found`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=trainer1",
      "--attacker=Monster1",
      "--attack=Tackle",
      "--target=NonExistentTarget"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("No monster with name 'NonExistentTarget' for opposing trainer")
  }

  @Test
  fun `handle throws exception if option is missing`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=",
      "--attacker=Monster1",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Missing argument: --trainer=")
  }

  @Test
  fun `matches returns true for correct input`() {
    val command = ChooseAttackCommand()
    val args = listOf(
      "battle",
      "attack",
      "--trainer=Ash",
      "--attacker=Balbasaur",
      "--attack=Razor Leaf",
      "--target=Squirtle"
    )

    val result = command.matches(args)

    assertThat(result).isTrue()
  }

  @Test
  fun `matches returns false if input doesn't match command`() {
    val command = ChooseAttackCommand()
    val args = listOf(
      "game",
      "new",
      "--trainer=Ash",
      "--attacker=Balbasaur",
      "--attack=Razor Leaf",
      "--target=Squirtle"
    )

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  /**
   * ChooseAction Command
   */


  @Test
  fun `matches returns false if Action command is wrong`() {

  }
}
