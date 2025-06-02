package monsterleague.gamelogic

enum class Type {
    NORMAL,
    FIRE,
    WATER,
    GRASS,
    GHOST
}

class TypeTable(
    private val inefficiencyMap: Map<Type, List<Type>> = mapOf(
        Type.NORMAL to listOf(Type.GHOST),
        Type.FIRE to listOf(Type.WATER, Type.FIRE),
        Type.WATER to listOf(Type.GRASS, Type.WATER),
        Type.GRASS to listOf(Type.FIRE, Type.GRASS),
        Type.GHOST to listOf(Type.NORMAL)
    ),

    private val efficiencyMap: Map<Type, List<Type>> = mapOf(
        Type.NORMAL to emptyList(),
        Type.FIRE to listOf(Type.GRASS),
        Type.WATER to listOf(Type.FIRE),
        Type.GRASS to listOf(Type.WATER),
        Type.GHOST to listOf(Type.GHOST)
    ))

    {
    fun inefficiencyOf(type: Type): List<Type> {
        return inefficiencyMap[type] ?: emptyList()
    }

    fun efficiencyOf(type: Type): List<Type> {
        return efficiencyMap[type] ?: emptyList()
    }
}
