package hwr.oop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FightTest : AnnotationSpec() {

    @Test
    fun `test Fight creation`() {
        // Arrange: Mock or provide required objects for Trainer and Monster
        val trainer = Trainer(
            name = "Ash",
            monster = emptyArray() // Provide an empty array or mock monsters
        )

        val monster = Monster(
            name = "Pikachu",
            type = Type(name = "Electric"),
            stats = Stats(
                hp = 100,
                attack = 55,
                defense = 40,
                speed = 90,
                spezDefense = 50,
                spezAttack = 20
            )
        )

        val fightID = 1
        val fightStatus = true
        val rounds = 3
        val winner = "Ash"

        // Act: Create a Fight instance
        val fight = Fight(
            fightID = fightID,
            fightStatus = fightStatus,
            trainers = trainer,
            monsters = monster,
            rounds = rounds,
            winner = winner
        )

        // Assert: Verify the Fight's properties
        assertThat(fight.fightID).isEqualTo(1)
        assertThat(fight.fightStatus).isTrue()
        assertThat(fight.trainers.name).isEqualTo("Ash")
        assertThat(fight.monsters.name).isEqualTo("Pikachu")
        assertThat(fight.rounds).isEqualTo(3)
        assertThat(fight.winner).isEqualTo("Ash")
    }
}