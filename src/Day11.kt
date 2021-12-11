typealias Octopuses = MutableMap<Point, Int>

fun main() {

    fun part1(input: Octopuses) =
        input.flashingSequence()
            .take(100)
            .sum()

    fun part2(input: Octopuses) =
        input.flashingSequence()
            .takeWhile { it < input.size }
            .count()
            .plus(1)

    val day = "11"
    val testInput = OctopusesInputReader.read("Day${day}_test")
    val input = OctopusesInputReader.read("Day$day")

    // part 1
    val testSolution1 = 1656
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)
    printResult(1, part1(input).also { check(it == 1723) })

    // part 2
    val testSolution2 = 195
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)
    printResult(2, part2(input).also { check(it == 327) })
}

// COROUTINES!
// Thanks to Todd Ginsberg for the blog article. I hope this is not too much of a copy :)
private fun Octopuses.flashingSequence(): Sequence<Int> = sequence {
    val octopuses = toMutableMap()

    while (true) {
        octopuses.forEach { (point, energy) ->
            octopuses[point] = energy + 1
        }

        val flashed = mutableSetOf<Point>()

        do {
            val currentFlashed = octopuses
                .filter { (it.value > 9) && (it.key !in flashed) }
                .keys
                .also { currentFlashed ->
                    flashed.addAll(currentFlashed)
                    currentFlashed
                        .flatMap { it.neighbors() }
                        .filter { (it in octopuses) && (it !in flashed) }
                        .forEach {
                            octopuses[it] = octopuses.getValue(it) + 1
                        }
                }
        } while (currentFlashed.isNotEmpty())

        flashed.forEach { octopuses[it] = 0 }

        yield(flashed.size)
    }
}

private fun Point.neighbors(): List<Point> =
    listOf(
        Point(x, y + 1),
        Point(x, y - 1),
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x - 1, y - 1),
        Point(x - 1, y + 1),
        Point(x + 1, y - 1),
        Point(x + 1, y + 1)
    )
