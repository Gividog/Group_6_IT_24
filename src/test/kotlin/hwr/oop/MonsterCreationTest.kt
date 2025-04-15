package hwr.oop.classes

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MonsterCreationTest : AnnotationSpec() {

    @Test
    fun `test Monster creation with Stats`() {
        // Arrange: Erstellen eines Stats-Objekts
        val stats = Stats(
            hp = 120,
            attack = 40,
            defense = 30,
            speed = 25,
            spezAttack = 50,
            spezDefense = 20
        )

        // Act: Erstellen eines Monster-Objekts
        val monster = Monster(
            name = "Goblin",
            type = Type.Fire,
            stats = stats,
            attacks = emptyList()
        )


        // Assert: Überprüfen, ob die Werte korrekt gesetzt wurden
        assertThat(monster.name).isEqualTo("Goblin")
        assertThat(monster.type.name).isEqualTo("Fire")
        assertThat(monster.stats.hp).isEqualTo(120)
        assertThat(monster.stats.attack).isEqualTo(40)
        assertThat(monster.stats.defense).isEqualTo(30)
        assertThat(monster.stats.speed).isEqualTo(25)
        assertThat(monster.stats.spezDefense).isEqualTo(20)
        assertThat(monster.stats.spezAttack).isEqualTo(50)
        assertThat(monster.attacks).isEmpty()

    }
}

