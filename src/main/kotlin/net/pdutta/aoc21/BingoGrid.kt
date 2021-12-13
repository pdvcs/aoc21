package net.pdutta.aoc21

data class Cell(val num: Int, var marked: Boolean = false)

class BingoGrid(private val rows: Int, private val columns: Int) {
    private var grid = Array(rows) { Array(columns) { Cell(0, false)  } }

    operator fun get(r: Int, c: Int): Cell? {
        return if (r in 0 until rows && c in 0 until columns) {
            grid[r][c]
        } else {
            null
        }
    }

    operator fun set(r: Int, c: Int, newValue: Cell) {
        grid[r][c] = newValue
    }

    private fun columns(): List<List<Cell>> {
        val cols = mutableListOf<List<Cell>>()
        for (c in 0 until this.columns) {
            val col = mutableListOf<Cell>()
            for (r in 0 until this.rows) {
                col.add(this[r,c]!!)
            }
            cols.add(col.toList())
        }
        return cols
    }

    private fun eachRow(): Sequence<Array<Cell>> = sequence {
        grid.forEach { r -> yield(r) }
    }

    fun eachCell(): Sequence<Cell> = sequence {
        grid.forEach { r ->
            r.forEach { c -> yield(c) }
        }
    }

    // Does the grid have any row or column fully marked?
    // (diagonals don't count)
    fun isBingo(): Boolean {
        for (r in eachRow()) {
            var allMarked = true
            r.forEach { allMarked = allMarked && it.marked }
            if (allMarked) return true
        }
        for (c in columns()) {
            var allMarked = true
            c.forEach { allMarked = allMarked && it.marked }
            if (allMarked) return true
        }
        return false
    }

    override fun toString(): String {
        val output = StringBuffer()
        for (r in eachRow()) {
            r.forEach { c ->
                output.append("%3d".format(c.num)).append(if (c.marked) "*" else " ").append(" ")
            }
            output.append("\n")
        }
        return output.toString()
    }
}
