package hwr.oop.monsterleague

import hwr.oop.monsterleague.cli.ChooseActionCommand
import hwr.oop.monsterleague.cli.CreateBattleCommand
import hwr.oop.monsterleague.cli.ChooseAttackCommand
import hwr.oop.monsterleague.cli.Cli
import hwr.oop.monsterleague.cli.CreateTrainerCommand
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.factories.BattleFactory
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice
import hwr.oop.monsterleague.gamelogic.Battle
import hwr.oop.monsterleague.gamelogic.Exceptions
import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.factories.MonsterFactory

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

class CliTest : AnnotationSpec() {

  /**
   * Setup of TrainerFactory and BattleFactory before and after every test
   */

  private val originalTrainers = TrainerFactory.getAll()

  @BeforeEach
  fun setup() {
    TrainerFactory.clear()
    TrainerFactory.save(TestData.trainerOutOfCombatAsh)
    TrainerFactory.save(TestData.trainerOutOfCombatMisty)

    BattleFactory.currentBattle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true
    )
  }

  @AfterEach
  fun resetTrainerFactory() {
    TrainerFactory.clear()
    originalTrainers.values.forEach { trainer ->
      TrainerFactory.save(trainer)
    }
  }

  /**
   * Cli Tests
   */

  @Test
  fun `Cli should route to CreateBattleCommand and create a battle`() {
    // given
    val cli = Cli()
    val args = listOf("battle", "new", "--trainers=Ash,Misty,0")

    // when
    val output = captureStandardOut {
      cli.handle(args)
    }.trim()

    // then
    assertThat(output).contains("Battle created with ID")
  }

  @Test
  fun `Cli should throw if no matching command`() {
    val cli = Cli()
    val args = listOf("invalid", "command")

    val ex = assertThrows<IllegalArgumentException> {
      cli.handle(args)
    }
    assertThat(ex.message).contains("No command found for arguments")
  }

  /**
   * CreateBattleCommand Tests
   */

  @Test
  fun `should create a new battle with given trainers`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Misty,1")

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
      .hasMessageContaining("Cannot create a battle with the same trainer twice: 'Ash'")
  }

  @Test
  fun `should fail if trainerOne not found`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Brock,Misty,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("You tried to select Brock but Brock is not available to choose.")
  }

  @Test
  fun `should fail if trainerTwo not found`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Brock,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("You tried to select Brock but Brock is not available to choose.")
  }

  @Test
  fun `should fail if expected format wasn't matched`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,0")

    val exception = kotlin.runCatching {
      command.handle(args)
    }.exceptionOrNull()

    assertThat(exception).isNotNull
      .hasMessageContaining("Expected format: --trainers=Trainer1,Trainer2,0 or 1")
  }

  @Test
  fun `should throw InvalidArgumentFormatException if damage flag is invalid`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,Misty,2")

    val ex = assertThrows<Exceptions.InvalidArgumentFormatException> {
      command.handle(args)
    }

    assertThat(ex.message).contains("Third argument must be 0 (simple damage) or 1 (complex damage), got '2'")
  }

  @Test
  fun `should throw MissingRequiredArgumentException if --trainers option missing`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new")

    val ex = assertThrows<Exceptions.MissingRequiredArgumentException> {
      command.handle(args)
    }

    assertThat(ex.message).contains("Missing required argument: --trainers=")
  }

  @Test
  fun `should throw EmptyArgumentException if trainer name one is blank`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=,Misty,0")

    val ex = assertThrows<Exceptions.EmptyArgumentException> {
      command.handle(args)
    }

    assertThat(ex.message).contains("Trainer names cannot be empty.")
  }

  @Test
  fun `should throw EmptyArgumentException if trainer name two is blank`() {
    val command = CreateBattleCommand()
    val args = listOf("battle", "new", "--trainers=Ash,,0")

    val ex = assertThrows<Exceptions.EmptyArgumentException> {
      command.handle(args)
    }

    assertThat(ex.message).contains("Trainer names cannot be empty.")
  }

  /**
   * CreateBattleCommand.matches() Tests
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
    BattleFactory.currentBattle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true
    )
    command = ChooseAttackCommand()
  }

  @AfterEach
  fun teardown() {
    BattleFactory.currentBattle = null
  }

  @Test
  fun `handle registers attack choice correctly`() {
    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true
    )
    BattleFactory.currentBattle = battle

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
    assertThat(ex.message).contains("You tried to select NonExistentTrainer but NonExistentTrainer is not available to choose.")
  }

  @Test
  fun `handle throws exception if attacker monster not found`() {
    val args = listOf(
      "battle", "attack",
      "--trainer=trainer4",
      "--attacker=NonExistentMonster",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentMonster")
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
    assertThat(ex.message).contains("You tried to select NonExistentAttack")
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
    assertThat(ex.message).contains("You tried to select NonExistentTarget")
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
    assertThat(ex.message).contains("Argument --trainer= must not be empty.")
  }

  @Test
  fun `handle throws exception if no active battle exists`() {
    BattleFactory.currentBattle = null

    val command = ChooseAttackCommand()
    val args = listOf(
      "battle", "attack",
      "--trainer=trainer1",
      "--attacker=Monster1",
      "--attack=Tackle",
      "--target=Monster3"
    )

    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("No active battle")
  }

  @Test
  fun `should throw MissingRequiredArgumentException if --trainer is missing`() {
    val args = listOf(
      "battle", "attack",
      "--attacker=Monster1",
      "--attack=Tackle",
      "--target=Monster3"
    )

    val ex = assertThrows<Exceptions.MissingRequiredArgumentException> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Missing required argument: --trainer=")
  }

  /**
   * ChooseAttackCommand.matches() Tests
   */

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
   * ChooseActionCommand Tests
   */

  @Test
  fun `ChooseActionCommand throws if no active battle exists`() {
    BattleFactory.currentBattle = null // explizit löschen
    val ex = assertThrows<Exception> {
      ChooseActionCommand()
    }
    assertThat(ex.message).contains("No active battle")
  }

  @Test
  fun `ChooseActionCommand handles attack choice correctly`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "attack",
      "--trainer=trainer1",
      "--attacker=Monster2",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val output = captureStandardOut {
      command.handle(args)
    }.trim()
    assertThat(output).contains("trainer1's action has been registered")
  }

  @Test
  fun `ChooseActionCommand handles switch choice correctly`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "switch",
      "--trainer=trainer1",
      "--out=Monster2",
      "--in=Monster1"
    )
    val output = captureStandardOut {
      command.handle(args)
    }.trim()
    assertThat(output).contains("trainer1's action has been registered")
  }

  @Test
  fun `ChooseActionCommand handles heal choice correctly`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "heal",
      "--trainer=trainer1",
      "--monster=Monster2"
    )
    val output = captureStandardOut {
      command.handle(args)
    }.trim()
    assertThat(output).contains("trainer1's action has been registered")
  }

  @Test
  fun `ChooseActionCommand handles surrender choice correctly`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "surrender",
      "--trainer=trainer1"
    )
    val output = captureStandardOut {
      command.handle(args)
    }.trim()
    assertThat(output).contains("trainer1's action has been registered")
  }

  @Test
  fun `ChooseActionCommand throws if action type missing`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Invalid command: Expected an action type at position 3 (attack, switch, heal, surrender).")
  }

  @Test
  fun `ChooseActionCommand throws on unknown action`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "dance",
      "--trainer=trainer1"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Unknown trainer action")
  }

  /**
   * ChooseCommand.matches() Tests
   */

  @Test
  fun `ChooseActionCommand matches returns true for correct format`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "attack",
      "--trainer=trainer1",
      "--attacker=Monster2",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val result = command.matches(args)
    assertThat(result).isTrue()
  }

  @Test
  fun `ChooseActionCommand matches returns false for wrong format`() {
    val command = ChooseActionCommand()
    val args = listOf(
      "battle", "start", "--trainer=trainer1"
    )
    val result = command.matches(args)
    assertThat(result).isFalse()
  }

  /**
   * CreateTrainerCommand Tests
   */

  @Test
  fun `CreateTrainerCommand should create a trainer with 3 monsters`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Ash",
      "--monsters=bulbasaur,charmander,squirtle"
    )

    val output = captureStandardOut {
      command.handle(args)
    }.trim()

    val trainer = TrainerFactory.findByName("Ash")
    assertThat(output).contains("Trainer created")
    assertThat(trainer).isNotNull
    assertThat(trainer!!.getListOfMonsters().size).isEqualTo(3)
    assertThat(trainer.getListOfMonsters().map { it.getName() })
      .containsExactly("Bulbasaur", "Charmander", "Squirtle")
  }

  @Test
  fun `CreateTrainerCommand should throw if not exactly 3 monsters`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Misty",
      "--monsters=bulbasaur,charmander"
    )

    val ex = assertThrows<IllegalArgumentException> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Exactly 3 monsters")
  }

  @Test
  fun `CreateTrainerCommand should throw if unknown monster is given`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Brock",
      "--monsters=bulbasaur,charmander,unknownmonster"
    )

    val ex = assertThrows<IllegalArgumentException> {
      command.handle(args)
    }
    assertThat(ex.message).contains("Unknown monster")
  }

  /**
   * CreateTrainerCommand.matches() Tests
   */

  @Test
  fun `CreateTrainerCommand matches returns true for correct command`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Ash",
      "--monsters=bulbasaur,charmander,squirtle"
    )
    assertThat(command.matches(args)).isTrue()
  }

  @Test
  fun `CreateTrainerCommand matches returns false for wrong command`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "game", "start",
      "--name=Ash",
      "--monsters=bulbasaur,charmander,squirtle"
    )
    assertThat(command.matches(args)).isFalse()
  }

  @Test
  fun `CreateTrainerCommand matches returns false if monsters missing`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Ash"
      // no monsters
    )
    assertThat(command.matches(args)).isFalse()
  }

  @Test
  fun `CreateTrainerCommand matches returns false if name option missing`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--monsters=bulbasaur,charmander,squirtle"
    )
    assertThat(command.matches(args)).isFalse()
  }

  @Test
  fun `CreateTrainerCommand matches returns false if monsters option missing`() {
    val command = CreateTrainerCommand(TrainerFactory)
    val args = listOf(
      "trainer", "create",
      "--name=Ash"
    )
    assertThat(command.matches(args)).isFalse()
  }
  @Test
  fun `MonsterFactory creates Gastly with correct stats and attacks`() {
    val gastly = MonsterFactory.create("gastly")

    assertThat(gastly.getName()).isEqualTo("Gastly")
    assertThat(gastly.getType()).isEqualTo(Type.GHOST)
    assertThat(gastly.getBaseStats().getInitiative()).isEqualTo(80)
    assertThat(gastly.getAttacks()).containsExactly(
      Attack.Burden, Attack.Lick, Attack.ShadowBall
    )
  }

  @Test
  fun `MonsterFactory creates Eevee with empty attack list`() {
    val eevee = MonsterFactory.create("eevee")

    assertThat(eevee.getName()).isEqualTo("Eevee")
    assertThat(eevee.getType()).isEqualTo(Type.NORMAL)
    assertThat(eevee.getBaseStats().getInitiative()).isEqualTo(55)
    assertThat(eevee.getAttacks()).isEmpty()
  }

  @Test
  fun `Trainer in Battle not found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "create", "attack",
    "--trainer=NonExistent"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistent")
  }

  @Test
  fun `chooseActionCommand Monster Not Found Exception`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "attack",
      "--trainer=trainer1",
      "--attacker=NotExistentMonster",
      "--attack=Tackle",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentMonster")
  }

  @Test
  fun `Trainer choose Action attack Not Found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "attack",
      "--trainer=trainer1",
      "--attacker=NotExistentMonster",
      "--attack=NonExistentAttack",
      "--target=Monster3"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentAttack")
  }

  @Test
  fun `targeted Monster not Found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "attack",
      "--trainer=trainer1",
      "--attacker=Monster3",
      "--attack=Tackle",
      "--target=NotExistentMonster"
    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentMonster")
  }

  @Test
  fun `Switch Choice Action Monster Not Found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "choice",
      "--out=NonExistentMonster",
      "--in=Monster1"

    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentMonster")
  }

  @Test
  fun `Switch Choice Action in Monster Not Found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "choice",
      "--out=Monster1",
      "--in=NonExistentMonster"

    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }
    assertThat(ex.message).contains("You tried to select NonExistentMonster")
  }

  @Test
  fun `heal action Monster not found`(){
    val command = ChooseActionCommand()
    val args = listOf(
      "trainer", "action", "heal",


    )
    val ex = assertThrows<Exception> {
      command.handle(args)
    }

  }


}
