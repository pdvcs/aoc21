package net.pdutta.aoc21

class Day05Vents(val debug: Boolean = false) {

    // Problem 9
    fun overlappingVents(ventInput: List<String>): Int {
        val gs = gridSize(ventInput)
        return overlapCount(ventInput, gs)
    }

    private fun gridSize(ventInput: List<String>): Int {
        var maxCoordinate = -1
        ventInput.forEach { line ->
            val matches = reg.find(line)
            val inputCoordinates = matches!!.groupValues.mapIndexed { i, v -> if (i > 0) v.toInt() else -1 }
            if (inputCoordinates.maxOrNull()!! > maxCoordinate) {
                maxCoordinate = inputCoordinates.maxOrNull()!!
            }
        }
        debug("maxCoordinate = $maxCoordinate")
        return maxCoordinate + 1
    }

    @Suppress("DuplicatedCode")
    private fun overlapCount(ventInput: List<String>, gridSize: Int): Int {

        val grid = Grid(gridSize, gridSize)

        for (line in ventInput) {
            val results = reg.find(line)
            val inputCoordinates = results!!.groupValues.mapIndexed { i, v -> if (i > 0) v.toInt() else -1 }
            val (_, x1, y1, x2, y2) = inputCoordinates

            debug("${x1},${y1} -> ${x2},${y2}")
            if (x1 == x2 || y1 == y2) {
                debug("This is a horiz/vert line")
                if (x1 == x2) {
                    debug("x1 = x2")
                    val li = listOf(y1, y2).sorted()
                    if (this.debug) { println("debug: coords are ") }
                    for (i in li[0]..li[1]) {
                        if (this.debug) { print("$x1,$i  ") }
                        grid[x1, i] = Cell(grid[x1, i]!!.num + 1)
                    }
                    if (this.debug) { println("") }
                }
                if (y1 == y2) {
                    debug("y1 = y2")
                    val li = listOf(x1, x2).sorted()
                    if (this.debug) { println("debug: coords are ") }
                    for (i in li[0]..li[1]) {
                        if (this.debug) { print("$i,$y1  ") }
                        grid[i, y1] = Cell(grid[i, y1]!!.num + 1)
                    }
                    if (this.debug) { println("") }
                }
            } else {
                debug("not horiz/vert")
            }
        }

        debug("\n${grid.str(Grid.ShowZeroesAsDots)}")
        var overlapCount = 0
        for (cell in grid.eachCell()) {
            if (cell.num > 1) {
                overlapCount++
            }
        }
        debug("overlapCount = $overlapCount")
        return overlapCount
    }

    private fun debug(s: String) {
        if (this.debug) {
            Utils.logDebug(s)
        }
    }

    companion object {
        private val reg = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")
    }
}
