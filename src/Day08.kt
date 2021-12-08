fun main() {

    fun part1(input: List<SevenSegmentDisplayPuzzle>) =
        input.sumOf { puzzle ->
            puzzle.output.filter { display ->
                display.isOne() || display.isFour() || display.isSeven() || display.isEight()
            }.size
        }

    fun part2(input: List<SevenSegmentDisplayPuzzle>) =
        input.sumOf { puzzle ->
            puzzle.output.map {
                val mapping = puzzle.correctMapping()
                digits.indexOf(
                    it.rewireWith(mapping)
                )
            }.joinToString(separator = "").toInt()
        }

    // test if implementation meets criteria from the description
    val testInput = SevenSegmentInputReader.read("Day08_test")

    // testing part 1
    val testSolution1 = 26
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2

    val testSolution2 = 61229
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)


    // the actual
    val input = SevenSegmentInputReader.read("Day08")
    printResult(1, part1(input).also { check(it == 237) })
    printResult(2, part2(input).also { check(it == 1_009_098) })
}

fun String.isOne()   = length == 2
fun String.isFour()  = length == 4
fun String.isSeven() = length == 3
fun String.isEight() = length == 7

data class SevenSegmentDisplayPuzzle(val hints: List<String>, val output: List<String>) {
    val allDisplays = hints + output
}

// the segments turned on in each number/index
val digits = listOf("123567", "36", "13457", "13467", "2346", "12467", "124567", "136", "1234567", "123467")

const val SEGMENTS = "abcdefg"

// what am i doing?
fun String.rewireWith(mapping: String) =
    map { mapping.indexOf(it) + 1 }
        .sorted()
        .joinToString(separator = "")

// this is madness!
fun String.allPossibleMappings(result: String = ""): List<String> =
    if (isEmpty())
        listOf(result)
    else
        flatMapIndexed { index, char ->
            removeRange(index, index + 1)
                .allPossibleMappings(result + char)
        }

// Hello world, we are brute forcing again!
fun SevenSegmentDisplayPuzzle.correctMapping(): String {
    var possibleMappings = SEGMENTS.allPossibleMappings()
    allDisplays.forEach { display ->
        possibleMappings = possibleMappings.filter { mapping ->
            display.rewireWith(mapping) in digits
        }
    }
    return possibleMappings.last() // last remaining mapping
}
