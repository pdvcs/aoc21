package net.pdutta.aoc21

import kotlin.math.absoluteValue

class Day05Vents(val debug: Boolean = false) {

    // Problem 9 and 10
    fun overlappingVents(ventInput: List<String>, countDiagonals: Boolean = false): Int {
        val gs = gridSize(ventInput)
        return overlapCount(ventInput, gs, countDiagonals)
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

    private fun overlapCount(ventInput: List<String>, gridSize: Int, countDiagonals: Boolean): Int {

        val grid = Grid(gridSize, gridSize)
        debug("note: ${if (countDiagonals) "" else "not "}counting diagonal lines")

        for (line in ventInput) {
            val results = reg.find(line)
            val inputCoordinates = results!!.groupValues.mapIndexed { i, v -> if (i > 0) v.toInt() else -1 }
            val (_, x1, y1, x2, y2) = inputCoordinates
            val slope = (y2 - y1) / (x2 - x1).toDouble()

            debug("${x1},${y1} -> ${x2},${y2}")
            if (x1 == x2 || y1 == y2) {
                debug("This is a horizontal/vertical line")
                if (x1 == x2) {
                    updateGrid(grid, y1, y2, fixedCoord = x1, CoordinateType.X)
                }
                if (y1 == y2) {
                    updateGrid(grid, x1, x2, fixedCoord = y1, CoordinateType.Y)
                }
            } else if (slope.absoluteValue == 1.0) {
                // lines inclined at 45 degrees are eligible to be counted, per the problem definition
                debug("This is a 45-degree inclined line")
                if (countDiagonals) updateGridDiagonal(grid, x1, y1, x2, y2)
            } else {
                debug("This isn't horizontal, vertical, or 45-degree inclined")
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

    // update cells along a diagonal line -- assumes the line has abs(slope)=1
    private fun updateGridDiagonal(grid: Grid, x1: Int, y1: Int, x2: Int, y2: Int) {
        val xf = if (x2 > x1) 1 else -1
        val yf = if (y2 > y1) 1 else -1
        trace("debug: coords are ")

        var x: Int
        var y: Int
        repeat((x2 - x1).absoluteValue + 1) { c ->
            x = x1 + c * xf
            y = y1 + c * yf
            trace("$x,$y  ")
            grid[x, y] = Cell(grid[x, y]!!.num + 1)
        }
        trace("\n")
    }

    private fun updateGrid(grid: Grid, coord1: Int, coord2: Int, fixedCoord: Int, fixedCoordType: CoordinateType) {
        val li = listOf(coord1, coord2).sorted()

        debug(if (fixedCoordType == CoordinateType.X) "x1 = x2" else "y1 = y2")
        trace("debug: coords are ")

        for (i in li[0]..li[1]) {
            when (fixedCoordType) {
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
