fun main() {

    fun part1(input: IntArray) = countSimulatedFishPopulation(fish = input, days = 80)

    fun part2(input: IntArray) = countSimulatedFishPopulation(fish = input, days = 256)

    // test if implementation meets criteria from the description
    val testInput = IntInputReader2.read("Day06_test")

    // testing part 1
    val testOutput1 = part1(testInput)
    val testSolution1 = 5934L
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2
    val testOutput2 = part2(testInput)
    val testSolution2 = 26_984_457_539L
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)

    // using the actual input for
    val input = IntInputReader2.read("Day06")
    printResult(1, part1(input).also { check(it == 352_151L) })
    printResult(2, part2(input).also { check(it == 1_601_616_884_019L) })
}

fun countSimulatedFishPopulation(fish: IntArray, days: Int) =
    fish.countByAgeGroup()
        .simulatePopulationIn(days)
        .sum()

fun IntArray.countByAgeGroup() =
    LongArray(9) { age -> filter { it == age }.size.toLong() }

fun LongArray.simulatePopulationIn(days: Int): LongArray =
    if (days > 0)
        simulatePopulationInOneDay()
            .simulatePopulationIn(days - 1)
    else this

fun LongArray.simulatePopulationInOneDay() =
    rotateToLeft(1) // simulates the aging/birth cycle
        .apply { this[6] += last() } // resets the mothers' cycles after birth

/**
 * Returns [LongArray] shifted by [amount] wrapping the first [amount] of elements around to the end.
 */
fun LongArray.rotateToLeft(amount: Int) = (drop(amount) + take(amount)).toLongArray()
