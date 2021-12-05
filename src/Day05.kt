fun main() {

    fun part1(input: List<Line>): Int {
        var diagram = diagram(input.neededDiagramSize())

        input.forEach {
            diagram = it.drawHorizontalOrVerticalLine(diagram)
        }
        return diagram.countDangerousAreas()
    }

    fun part2(input: List<Line>): Int {
        var diagram = diagram(input.neededDiagramSize())

        input.forEach { cloudLine ->
            diagram = cloudLine.drawAnyLine(diagram)
        }
        return diagram.countDangerousAreas()
    }

    // test if implementation meets criteria from the description
    val testInput = VentCloudInputReader.read("Day05_test")

    // testing part 1
    val testSolution1 = 5
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)

    // testing part 2
    val testSolution2 = 12
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)

    // using the actual input for
    val input = VentCloudInputReader.read("Day05")
    printResult(1, part1(input))
    printResult(2, part2(input))
}

data class Point(val x: Int, val y: Int)
data class Line(val from: Point, val to: Point)

typealias Diagram = List<List<Int>>
fun diagram(size: Int) = List(size) { List(size) { 0 } }

fun List<Line>.neededDiagramSize() =
    maxOf { line ->
        listOf(line.from.x, line.from.y, line.to.x, line.to.y).maxOf { it }
    } + 1 // add 1 for size so that biggest index fits inside

fun Line.isVertical() = from.x == to.x

fun Line.isHorizontal() = from.y == to.y

fun Line.drawAnyLine(diagram: Diagram) =
    if (isHorizontal() || isVertical())
        drawHorizontalOrVerticalLine(diagram)
    else // this assumes that every Line is either horizontal, vertical, or diagonal
        drawDiagonalLine(diagram)

/**
 * Draws given horizontal or vertical [Line] in the [givenDiagram].
 */
fun Line.drawHorizontalOrVerticalLine(givenDiagram: Diagram): Diagram =
    givenDiagram.map { it.toMutableList() }.toMutableList().apply {
        when {
            isHorizontal() -> for (i in from.x upTo to.x) this[i][from.y]++
            isVertical()   -> for (i in from.y upTo to.y) this[from.x][i]++
        }
}

/**
 * Draws given diagonal [Line] in the [givenDiagram].
 */
fun Line.drawDiagonalLine(givenDiagram: Diagram): Diagram {
    val diagram = givenDiagram.map { it.toMutableList() }.toMutableList()

    val xDirection = from.x direction to.x
    val yDirection = from.y direction to.y

    val range = 0..(from.x upTo to.x).rangeCount()

    for (index in range)
        diagram[index.xDirection(from.x)][index.yDirection(from.y)]++

    return diagram
}

infix fun Int.direction(other: Int): Int.(Int) -> Int =
    if (this > other)
        { a -> a - this }
    else
        { a -> a + this }

fun IntRange.rangeCount() = last - first

fun Diagram.countDangerousAreas() = sumOf { row -> row.filter { it >= 2 }.size }
