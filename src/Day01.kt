fun main() {
    /**
     * Counts the increases from the given input measurements from one to another.
     */
    fun part1(input: List<Int>) = input.countIncreases()

    /**
     * Counts the increases from the given input measurements from the one triplet sum to another.
     */
    fun part2(input: List<Int>) = input.countIncreases(groupSize = 3)

    runEverything("01", 7, 5, ::part1, ::part2, IntInputReader)
}

private fun List<Int>.countIncreases(groupSize: Int = 1) =
    windowed(groupSize + 1, 1) {
        it.first() < it.last()
    }.filter { it }.size
