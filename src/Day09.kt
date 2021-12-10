typealias HeightMap = List<List<Int>>
typealias Basin = List<Position>
typealias UnexploredBasin = MutableList<Position>

fun main() {

    fun part1(map: HeightMap) =
        map.foldIndexed(mutableListOf<Int>()) { x, acc, row ->
            row.forEachIndexed { y, number ->
                if (map.hasLowestPointAt(x, y))
                    acc.add(number + 1)
            }
            acc
        }.sum()

    fun part2(map: HeightMap): Int {
        val basins = mutableListOf<Basin>()

        for (x in map.indices)
            for (y in map[x].indices)
                if (map.hasLowestPointAt(x, y))
                    basins.add(map.exploreBasinAt(x, y))

        return basins
            .sortedBy { it.size }
            .takeLast(3) // take largest
            .map { it.size }
            .product()
    }

    val day = "09"
    val testInput = HeightMapInputReader.read("Day${day}_test")
    val input = HeightMapInputReader.read("Day$day")

    // part 1
    val testSolution1 = 15
    val testOutput1 = part1(testInput)
    printTestResult(1, testOutput1, testSolution1)
    check(testOutput1 == testSolution1)
    printResult(1, part1(input).also { check(it == 502) })

    // part 2
    val testSolution2 = 1134
    val testOutput2 = part2(testInput)
    printTestResult(2, testOutput2, testSolution2)
    check(testOutput2 == testSolution2)
    printResult(2, part2(input).also { check(it == 1330560) })
}

data class Position(val x: Int, val y: Int)

val Position.neighbors get() = listOf(
    Position(x, y - 1),
    Position(x, y + 1),
    Position(x - 1, y),
    Position(x + 1, y),
)

fun HeightMap.hasLowestPointAt(x: Int, y: Int) = hasLowestPointAt(Position(x, y))

fun HeightMap.hasLowestPointAt(position: Position) =
    position.neighbors.all { neighbor ->
        getOrMax(position) < getOrMax(neighbor)
    }

fun HeightMap.getOrMax(p: Position) = getOrNull(p.x)?.getOrNull(p.y) ?: Int.MAX_VALUE

fun HeightMap.exploreBasinAt(x: Int, y: Int) = exploreBasin(mutableListOf(Position(x, y)))

fun HeightMap.exploreBasin(exploredPart: UnexploredBasin): Basin {
    val beforeExploredPart = exploredPart.toList() // makes a copy to compare against

    for (basinPosition in beforeExploredPart)
        for (neighbor in basinPosition.neighbors)
            if (isNewPartOfBasin(neighbor, exploredPart))
                exploredPart.add(neighbor)

    return if (exploredPart.size > beforeExploredPart.size) // explored something new?
        exploreBasin(exploredPart) // here we go recurse again!
    else exploredPart
}

fun HeightMap.isNewPartOfBasin(possibleBasinPart: Position, exploredPart: UnexploredBasin) =
    exploredPart.containsNot(possibleBasinPart) && getOrMax(possibleBasinPart) < 9
