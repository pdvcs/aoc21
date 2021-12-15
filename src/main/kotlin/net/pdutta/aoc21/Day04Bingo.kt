package net.pdutta.aoc21

class Day04Bingo(val debug: Boolean = false) {

    // Problem 8
    // Pick the last board to win
    fun reverseBingo(input: String): Int {
        val (numbers, grids, numberMap) = parseBingoInput(input)
        val completedGridIndices = hashMapOf<Int, Boolean>()
        for (n in numbers) {
            if (numberMap.containsKey(n)) {
                for ((gridId, row, column) in numberMap[n]!!) {
                    grids[gridId][row, column]?.marked = true
                }
                for (x in grids.indices) {
                    val g = grids[x]
                    if (g.isBingo()) {
                        completedGridIndices[x] = true
                        if (completedGridIndices.keys.size == grids.size) {
                            debug(">> last bingo! called number = $n, grid id = $x, grid = \n$g")
                            return bingoScore(g, n)
                        }
                    }
                }
            }
        }
        return -1
    }

    // Problem 7
    fun bingoGame(input: String): Int {
        val (numbers, grids, numberMap) = parseBingoInput(input)
        for (n in numbers) {
            if (numberMap.containsKey(n)) {
                for ((gridId, row, column) in numberMap[n]!!) {
                    grids[gridId][row, column]?.marked = true
                }
                for (x in grids.indices) {
                    val g = grids[x]
                    if (g.isBingo()) {
                        debug(">> bingo! called number = $n, grid id = $x, grid = \n$g")
                        return bingoScore(g, n)
                    }
                }
            }
        }
        return -1
    }

    private fun bingoScore(g: Grid, numberCalledAtWin: Int): Int {
        var total = 0
        for (cell in g.eachCell()) {
            total += if (!cell.marked) cell.num else 0
        }
        return total * numberCalledAtWin
    }

    /**
     * @param s a string representing the input per the AoC problem definition.
     * @return A BingoInputParseResult with the Bingo numbers, the Bingo Grids, and a Dictionary.
     *      numbers: List<Int> is a list of input numbers per the AoC problem definition,
     *      grids: List<BingoGrid> is a list of the input 5x5 grids per the AoC problem definition,
     *      numberMap: is a Dictionary of where each number N in the grid can be found,
     *          ... Which grid, and which row and column.
     *          N (int) -> [gridId, row, column], [gridId, row, column], ...
     *          gridId, row, and column are all zero-based.
     */
    private fun parseBingoInput(s: String): BingoInputParseResult {
        val lines = s.split("\n")
        val numbers = lines[0].split(",").map { it.toInt() }

        val numberMap = hashMapOf<Int, MutableList<Triple<Int, Int, Int>>>()
        val grids = mutableListOf<Grid>()
        var grid = Grid(5, 5)
        var gridId = 0
        var row = -1
        for (i in 2 until lines.size) {
            val line = lines[i].trim()
            if (line == "") {
                grids.add(grid)
                grid = Grid(5, 5)
                gridId++
                row = -1
            } else {
                row++
                val lineNums = line.split(regex = """\s+""".toRegex()).map { it.toInt() }
                lineNums.forEachIndexed { col, num ->
                    grid[row, col] = Cell(num)
                    if (numberMap.containsKey(num)) {
                        numberMap[num]!!.add(Triple(gridId, row, col))
                    } else {
                        numberMap[num] = mutableListOf(Triple(gridId, row, col))
                    }
                }
            }
        }
        grids.add(grid)

        return BingoInputParseResult(numbers, grids.toList(), numberMap)
    }

    private fun debug(s: String) {
        if (this.debug) {
            Utils.logDebug(s)
        }
    }
}

/**
 * @see Day04Bingo.parseBingoInput
 */
private data class BingoInputParseResult(
    val numbers: List<Int>,
    val grids: List<Grid>,
    val numberMap: HashMap<Int, MutableList<Triple<Int, Int, Int>>>
)
