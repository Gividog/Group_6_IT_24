package hwr.oop.monsterleague

import hwr.oop.monsterleague.cli.BattleHolder
import hwr.oop.monsterleague.cli.CreateBattleCommand
import hwr.oop.monsterleague.cli.ChooseActionCommand
import hwr.oop.monsterleague.cli.ChooseAttackCommand
import hwr.oop.monsterleague.gamelogic.trainers.Trainer
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.Monster

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import hwr.oop.monsterleague.gamelogic.BaseStats
import hwr.oop.monsterleague.gamelogic.BattleStats
import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.factories.BattleFactory
import org.assertj.core.api.Assertions.assertThat

class CliTest : AnnotationSpec() {

  /**
   * Setup of TrainerFactory before and after every test
   */

  private val originalTrainers = TrainerFactory.getAll()

  @BeforeEach
  fun setup() {
    TrainerFactory.clear()
    val monsters = listOf(Monster(
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
    ))
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
      .hasMessageContaining("Cannot find trainer Ash")
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



  /**
   * matches() Tests (ChooseActionCommand)
   */

  @Test
  fun `matches returns true for correct input`() {
    val command = ChooseAttackCommand(BattleHolder.currentBattle?)
    val args = listOf("battle", "attack", "--trainer=Ash", "--attacker=Balbasaur", "--attack=Razor Leaf", "--target=Squirtle")

    val result = command.matches(args)

    assertThat(result).isTrue()
  }


  @Test
  fun `matches returns false if input doesn't match command`() {
    val command = ChooseAttackCommand()
    val args = listOf("game", "new", "--trainer=Ash", "--attacker=Balbasaur", "--attack=Razor Leaf", "--target=Squirtle")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  @Test
  fun `matches returns false if either trainer or attacker or attack or target is missing at all`() {
    val command = ChooseAttackCommand()
    val args = listOf("battle", "attack", "--trainer=Ash", "--attacker=Balbasaur", "--attack=Razor Leaf")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }

  @Test
  fun `matches returns false if there's no input for either trainer or attacker or attack or target`() {
    val command = ChooseAttackCommand()
    val args = listOf("battle", "attack", "--trainer=Ash", "--attacker=", "--attack=Razor Leaf", "--target=Squirtle")

    val result = command.matches(args)

    assertThat(result).isFalse()
  }
}
