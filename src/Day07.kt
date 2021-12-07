import kotlin.math.absoluteValue

fun main() {

    fun part1(input: IntArray) =
        input.sumOf { crabPosition ->
            crabPosition distanceTo input.cheapestPointByDistance()
        }

    fun part2(input: IntArray) =
        // the outer part is brute forcing by trying every possible alignment position
        input.rangeMinToMax().minOf { possibleMeetingPosition ->
            input.sumOf { crabPosition ->
                (crabPosition distanceTo possibleMeetingPosition)
                    .fuelNeeded()
            }
        }

    // test if implementation meets criteria from the description
    val testInput = IntInputReader2.read("Day07_test")

    // testing part 1
    val testSolution1 = 37
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2
    val testSolution2 = 168
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)

    // the actual
    val input = IntInputReader2.read("Day07")
    printResult(1, part1(input).also { check(it == 347_449) })
    printResult(2, part2(input).also { check(it == 98_039_527) })
}

infix fun Int.distanceTo(location: Int) = (this - location).absoluteValue

fun IntArray.cheapestPointByDistance() = sortedArray()[size / 2]

fun IntArray.rangeMinToMax() = sortedArray().run { first()..last() }

/**
 * Part 2 fuel cost.
 * Optimized way to get the sum of [Int] and all numbers below it.
 */
fun Int.fuelNeeded() = this * (this + 1) / 2
