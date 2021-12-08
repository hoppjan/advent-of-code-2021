import java.io.File

fun readFileLines(name: String) = File("src", "$name.txt").readLines()

fun interface InputReader<T> {
    fun read(name: String): List<T>
}

// Day 01
object IntInputReader: InputReader<Int> {
    override fun read(name: String) = readFileLines(name).map { it.toInt() }
}

// Day 02
object InstructionInputReader: InputReader<Instruction> {
    override fun read(name: String) = readFileLines(name).map {
        Instruction(it.substringBefore(" "), it.substringAfter(" ").toInt())
    }
}

// Days 03 & 07
object StringInputReader: InputReader<String> {
    override fun read(name: String) = readFileLines(name)
}

// Day 04
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

// Day 05
object VentCloudInputReader {
    fun read(name: String) = readFileLines(name).map { it.toLine() }

    private fun String.toLine(): Line = split(" -> ")
        .let { Line(it.first().toPoint(), it.last().toPoint()) }

    private fun String.toPoint(): Point = split(",")
        .let { Point(it.first().toInt(), it.last().toInt()) }
}

// Day 06
object IntInputReader2 {
    fun read(name: String) = readFileLines(name).first().split(",").map { it.toInt() }.toIntArray()
}

// Day 08
object SevenSegmentInputReader {
    fun read(name: String) = readFileLines(name).map { line ->
        line.split(" | ")
            .let { it.first().toDisplayList() puzzleTo it.last().toDisplayList() }
    }

    private fun String.toDisplayList() = trim().split(" ")

    private infix fun List<String>.puzzleTo(output: List<String>) =
        SevenSegmentDisplayPuzzle(this, output)
}
