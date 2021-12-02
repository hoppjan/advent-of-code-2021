import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts content of the given text file to List<Int>.
 */
fun readInputInts(name: String) = readInput(name).map { it.toInt() }

/**
 * Converts content of the given text file to [Instruction] list.
 */
fun readInputInstructions(name: String) = readInput(name).map {
    Instruction(it.substringBefore(" "), it.substringAfter(" ").toInt())
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
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
