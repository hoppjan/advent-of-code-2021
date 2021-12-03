fun main() {
    /**
     * Calculates binary [String] gamma and multiplies it with its inversion, epsilon.
     * @return decimal power consumption (product of gamma and epsilon)
     */
    fun part1(input: List<String>): Int {
        val gamma = input.gammaString()
        val epsilon = gamma.invertBitString()

        return gamma.toInt(radix = 2) * epsilon.toInt(radix = 2)
    }

    /**
     * Calculates o2 and co2 ratings and
     * @return product of the ratings
     */
    fun part2(input: List<String>) = input.filterO2Rating() * input.filterCO2Rating()

    runEverything("03", 198, 230, ::part1, ::part2, StringInputReader)
}

/**
 * Builds a binary [String] consisting of the more common [Char] for each column ('0' or '1').
 */
private fun List<String>.gammaString(): String {
    val counted = '0'
    val counters = MutableList(first().length) { 0 } // counter for each column

    forEach { line ->
        line.forEachIndexed { index, char ->
            if (char == counted)
                counters[index]++
        }
    }

    return buildString {
        for (i in counters)
            append(if (i > this@gammaString.size / 2) counted else '1')
    }
}

private fun String.invertBitString() = buildString {
    for (bitChar in this@invertBitString)
        append(if (bitChar == '1') 0 else 1)
}

/**
 * Filters the remaining [List] of binary [String]s for the more common [Char] in every column.
 * @return the last remaining matching binary [String] as an [Int]
 */
private fun List<String>.filterGasRating(filterRule: (toMatch: Char, matcher: Char) -> Boolean): Int {
    var filteredList = this

    for (i in first().indices) {
        filteredList = filteredList.filter {
            filterRule(it[i], filteredList.gammaString()[i])
        }

        if (filteredList.size == 1) break
    }
    return filteredList.first().toInt(radix = 2)
}

// To be honest, I have no clue anymore how this filterRule works exactly,
//  but it was the only difference between o2 and co2 rating.
private fun List<String>.filterO2Rating () = filterGasRating { toMatch, matcher -> toMatch != matcher }
private fun List<String>.filterCO2Rating() = filterGasRating { toMatch, matcher -> toMatch == matcher }
