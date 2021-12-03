fun main() {

    /**
     * Follows the navigation instructions as they are misunderstood
     * @param input navigation instructions
     * @return [Coordinates.result]
     */
    fun part1(input: List<Instruction>) =
        Coordinates()
            .navigate(instructions = input)
            .result

    /**
     * Follows the navigation instructions considering [Coordinates.aim]
     * @param input navigation instructions
     * @return [Coordinates.result]
     */
    fun part2(input: List<Instruction>) =
        Coordinates(isAimOn = true)
            .navigate(instructions = input)
            .result

    runEverything("02", 150, 900, ::part1, ::part2, InstructionInputReader)
}

/**
 * Representation of a navigation instruction
 * @param direction should only ever be [Directions.FORWARD], [Directions.UP] or [Directions.DOWN]
 * (an enum would have been a little much here)
 * @param value how far to follow the [direction]
 */
data class Instruction(val direction: String, val value: Int) {

    init {
        check(direction == FORWARD || direction == UP || direction == DOWN)
    }

    companion object Directions {
        const val FORWARD = "forward"
        const val UP = "up"
        const val DOWN = "down"
    }
}

/**
 * Representation of location and aim.
 * Backwards compatible with part 1 (ignoring aim and misunderstanding how navigation works),
 * but also works with [aim] for part 2, if [isAimOn] is set to true.
 */
data class Coordinates(
    private var x: Int = 0,
    private var y: Int = 0,
    private var aim: Int = 0,
    private val isAimOn: Boolean = false,
) {
    val result get() = x * y

    private fun forward(forward: Int) {
        x += forward
        if (isAimOn)
            y += (forward * aim)
    }

    private fun up(up: Int) {
        if (isAimOn.not())
            y -= up
        else
            aim -= up
    }

    private fun down(down: Int) {
        if (isAimOn.not())
            y += down
        else
            aim += down
    }

    fun navigate(instructions: List<Instruction>): Coordinates {
        instructions.forEach {
            when (it.direction) {
                Instruction.FORWARD ->
                    forward(it.value)
                Instruction.UP ->
                    up(it.value)
                Instruction.DOWN ->
                    down(it.value)
                else -> throw Exception("Unknown direction!")
            }
        }
        return this
    }
}
