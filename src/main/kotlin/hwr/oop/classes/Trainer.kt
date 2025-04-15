package hwr.oop.classes

class Trainer(val name: String, var monsters: List<Monster> = emptyList() ) {

    companion object {
        var allTrainers: List<Trainer> = listOf()

        fun register(trainer: Trainer) {
            allTrainers = allTrainers + trainer
        }

        fun getAll(): List<Trainer> = allTrainers.toList()
    }
}