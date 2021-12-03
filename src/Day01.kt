fun main() {
    /**
     * Counts the increases from the given input measurements from one to another.
     */
    fun part1(input: List<Int>) = input.countIncreases()

    /**
     * Counts the increases from the given input measurements from the one triplet sum to another.
     */
    fun part2(input: List<Int>) = input.addTriplets().countIncreases()

    runEverything("01", 7, 5, ::part1, ::part2, IntInputReader)
}

/**
 * Invokes [onIncrease] every time the next number is bigger than its predecessor.
 */
private fun List<Int>.forEachIncrease(onIncrease: () -> Unit) {
    for (i in 0 until lastIndex)
        if (get(i) < get(i + 1))
            onIncrease()
}

/**
 * Counts the increases of a [List<Int>] from one number to the next.
 */
private fun List<Int>.countIncreases(): Int {
    var increases = 0
    forEachIncrease {
        increases++
    }
    return increases
}

/**
 * Adds up element and its following two neighbors as long as the two following neighbors exist.
 */
private fun List<Int>.addTriplets(): List<Int> =
    mutableListOf<Int>().let { triplets ->
        for (i in 0..lastIndex - 2)
            triplets.add(get(i) + get(i + 1) + get(i + 2))
        triplets
    }
