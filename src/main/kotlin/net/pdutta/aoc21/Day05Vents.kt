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
                    updateGrid(grid, y1, y2, fixedCoord = x1, CoordinateType.X)
                }
                if (y1 == y2) {
                    updateGrid(grid, x1, x2, fixedCoord = y1, CoordinateType.Y)
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

    private fun updateGrid(grid: Grid, coord1: Int, coord2: Int, fixedCoord: Int, fixedCoordType: CoordinateType) {
        val li = listOf(coord1, coord2).sorted()

        debug(if (fixedCoordType == CoordinateType.X) "x1 = x2" else "y1 = y2")
        trace("debug: coords are ")

        for (i in li[0]..li[1]) {
            when(fixedCoordType) {
                CoordinateType.X -> {
                    trace("$fixedCoord,$i  ")
                    grid[fixedCoord, i] = Cell(grid[fixedCoord, i]!!.num + 1)
                }
                CoordinateType.Y -> {
                    trace("$i,$fixedCoord  ")
                    grid[i, fixedCoord] = Cell(grid[i, fixedCoord]!!.num + 1)
                }
            }
        }
        trace("\n")
    }

    private fun debug(s: String) {
        if (this.debug) {
            Utils.logDebug(s)
        }
    }

    private fun trace(s: String) {
        if (this.debug) {
            print(s)
        }
    }

    companion object {
        private val reg = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")
    }
}

enum class CoordinateType {
    X, Y
}
