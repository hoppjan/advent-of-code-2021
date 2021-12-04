typealias BingoBoard = List<List<Int>>

fun main() {

    fun part1(numbers: List<Int>, bingoBoards: List<BingoBoard>): Int {
        val drawnNumbers = mutableListOf<Int>()

        numbers.forEach { number ->
            drawnNumbers.add(number)
            bingoBoards.firstOrNull {
                it.hasBingo(drawnNumbers)
            }?.run {
                return number * unmarkedFieldSum(drawnNumbers)
            }
        }
        throw Exception("Better luck next time!")
    }

    fun part2(numbers: List<Int>, bingoBoards: List<BingoBoard>): Int {
        var boards = bingoBoards
        val drawnNumbers = mutableListOf<Int>()

        numbers.forEach { number ->
            drawnNumbers.add(number)
            val board = boards.first()
            if (boards.onlyOneRemaining() && board.hasBingo(drawnNumbers))
                return number * board.unmarkedFieldSum(drawnNumbers)
            else
                boards = boards.filter {
                    it.hasBingo(drawnNumbers).not()
                }
        }
        throw Exception("Better luck next time!")
    }

    // test if implementation meets criteria from the description
    val (testNumbers, testBingoBoards) =
        BingoBoardInputReader.read("Day04_test")

    // testing part 1
    val testSolution1 = 4512
    val testOutput1 = part1(testNumbers, testBingoBoards)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2
    val testSolution2 = 1924
    val testOutput2 = part2(testNumbers, testBingoBoards)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)

    // using the actual input for
    val (numbers, boards) = BingoBoardInputReader.read("Day04")
    printResult(1, part1(numbers, boards))
    printResult(2, part2(numbers, boards))
}

fun List<*>.onlyOneRemaining() = size == 1

fun BingoBoard.unmarkedFieldSum(drawnNumbers: List<Int>) =
    sumOf { numbers ->
        numbers.filterNot {
            drawnNumbers.contains(it)
        }.sum()
    }

fun BingoBoard.hasBingo(drawnNumbers: List<Int>) =
    any {
        drawnNumbers.checkRowForBingo(it)
    } or rotate().any {
        drawnNumbers.checkRowForBingo(it)
    }

fun List<Int>.checkRowForBingo(row: List<Int>) = containsAll(row)

fun BingoBoard.rotate() =
    indices.map { index ->
        map { it[index] }
    }
