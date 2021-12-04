import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readFileLines(name: String) = File("src", "$name.txt").readLines()

fun interface InputReader<T> {
    fun read(name: String): List<T>
}

object IntInputReader: InputReader<Int> {
    override fun read(name: String) = readFileLines(name).map { it.toInt() }
}

object StringInputReader: InputReader<String> {
    override fun read(name: String) = readFileLines(name)
}

object InstructionInputReader: InputReader<Instruction> {
    override fun read(name: String) = readFileLines(name).map {
        Instruction(it.substringBefore(" "), it.substringAfter(" ").toInt())
    }
}

object BingoBoardInputReader {
    fun read(name: String): Pair<List<Int>, List<BingoBoard>> {
        val fileLines = readFileLines(name)
        val bingoNumbers = fileLines[0].split(",").map { it.toInt() }

        val boards = fileLines
            .drop(2)
            .filterNot { it.isBlank() }
            .chunked(5) { lines ->
                lines.map { line ->
                    line.trim()
                        .replace("  ", " ")
                        .split(" ")
                        .map { it.toInt() }
                }
            }
        return bingoNumbers to boards
    }
}

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
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
