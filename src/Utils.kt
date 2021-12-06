
/**
 * Prints test result of a part and solution.
 */
fun <T> printTestResult(part: Int = 1, result: T, expected: T) =
    println("test result $part: $result, $expected expected")

/**
 * Prints result of a part.
 */
fun <T> printResult(part: Int, result: T) =
    println("result part $part: $result")

/**
 * Boilerplate code to run both parts on test and actual inputs.
 */
fun <I> runEverything(
    day: String,
    testSolution1: Int,
    testSolution2: Int,
    part1: (List<I>) -> Int,
    part2: (List<I>) -> Int,
    inputReader: InputReader<I>,
) {

    // test if implementation meets criteria from the description
    val testInput = inputReader.read("Day${day}_test")

    // testing part 1
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)

    // using the actual input for
    val input = inputReader.read("Day$day")
    printResult(1, part1(input))
    printResult(2, part2(input))
}

/**
 * Returns [IntRange] or the reversed [IntRange] if the first [Int] is bigger.
 * This is necessary as descending [IntRange]s are just empty by default.
 */
infix fun Int.upTo(limit: Int) =
    if (this < limit)
        this..limit
    else
        limit..this
