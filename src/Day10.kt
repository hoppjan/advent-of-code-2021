fun main() {

    fun part1(input: List<String>) =
        input.sumOf { line ->
            line.lineScore()
        }

    fun part2(input: List<String>) =
        input.filterNotCorrupted()
            .map { it.completionScore() }
            .median()

    val day = "10"
    val testInput = StringInputReader.read("Day${day}_test")
    val input = StringInputReader.read("Day$day")

    // part 1
    val testSolution1 = 26397
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)
    printResult(1, part1(input).also { check(it == 216297) })

    // part 2
    val testSolution2 = 288957L
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)
    printResult(2, part2(input).also { check(it == 2165057169L) })
}

private fun String.lineScore() = analyzeBrackets(default = 0) { scoreMapPartOne[it] ?: 0 }

private fun String.completionScore(): Long =
    openBrackets { openedBrackets ->
        openedBrackets
            .mapNotNull { scoreMapPartTwo[brackets[it]] }
            .reversed()
            .fold(0L) { acc, i ->
                acc * 5 + i
            }
    }

private fun String.openBrackets(withOpenBrackets: (MutableList<Char>) -> Long) =
    analyzeBrackets(withOpenBrackets) { 0L }

private fun List<String>.filterNotCorrupted() = filter { it.isNotCorrupted() }

private fun String.isNotCorrupted() = analyzeBrackets(default = true) { false }

private fun <R> String.analyzeBrackets(default:  R, onUnexpected: (Char) -> R) =
    analyzeBrackets({ default }, onUnexpected)

private fun <R> String.analyzeBrackets(onDefault: (MutableList<Char>) -> R, onUnexpected: (Char) -> R): R {
    val opened = mutableListOf<Char>()

    for (bracket in this)
        when (bracket) {
            in brackets ->
                opened.add(bracket)
            brackets[opened.lastOrNull()] ->
                opened.removeLast()
            else ->
                return onUnexpected(bracket)
        }

    return onDefault(opened)
}

fun List<Long>.median() = sorted().let { it[it.size / 2] }

val brackets = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

val scoreMapPartOne = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

val scoreMapPartTwo = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4,
)
