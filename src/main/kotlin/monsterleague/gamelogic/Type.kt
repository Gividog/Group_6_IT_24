package monsterleague.gamelogic

enum class Type(
    val efficiency: List<String>,
    val inefficiency: List<String>
) {
    FIRE(efficiency = listOf("GRASS"), inefficiency = listOf("WATER", "FIRE")),
    WATER(efficiency = listOf("FIRE","GROUND"), inefficiency = listOf("GRASS","WATER")),
    GRASS(efficiency = listOf("WATER", "GROUND"), inefficiency = listOf("FIRE", "GRASS")),
    GROUND(efficiency = listOf("FIRE", "ELECTRIC"), inefficiency =  listOf("WATER", "GROUND", "GRASS")),
    ELECTRIC(efficiency = listOf("WATER"), inefficiency = listOf("GROUND", "ELECTRIC", "GRASS")),
}