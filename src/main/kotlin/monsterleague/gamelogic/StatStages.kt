package monsterleague.gamelogic

enum class StatStages(var stage: Int, val multiplier: Double) {

    MINUS_ONE(-1, 0.25 ),
    MINUS_TWO(-2, 0.28),
    MINUS_THREE(-3, 0.33),
    MINUS_FOUR(-4, 0.4),
    MINUS_FIVE(-5, 0.5),
    MINUS_SIX(-6, 0.66),
    ZERO(0, 1.0),
    PLUS_ONE(1, 1.5),
    PLUS_TWO(2, 2.0),
    PLUS_THREE(3, 2.5),
    PLUS_FOUR(4, 3.0),
    PLUS_FIVE(5, 3.5),
    PLUS_SIX(6, 4.0)

}

fun increaseStage(statStages: StatStages) {

}
fun decreaseStage(statStages: StatStages) {

}