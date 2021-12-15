package net.pdutta.aoc21

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AoCTest {

    //<editor-fold desc="test data">

    private val day1input = "199 200 208 210 200 207 240 269 260 263".split(" ").map { it.toInt() }
    private val day2input = """
                                forward 5
                                down 5
                                forward 8
                                up 3
                                down 8
                                forward 2
                            """.trimIndent().split("\n")
    private val day3input = "00100 11110 10110 10111 10101 01111 00111 11100 10000 11001 00010 01010".split(" ")
    private val day4input = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
        """.trimIndent()
    private val day5input = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
        """.trimIndent().split("\n")

    //</editor-fold>

    @Test
    fun testDay01To03() {
        val p = Day01To03(debug = true)

        println("\n== testDay01 ==")
        assertEquals(7, p.measureSweep(day1input))
        assertEquals(5, p.measureSweepWithSlidingWindow(day1input))

        println("\n== testDay02 ==")
        assertEquals(150, p.diveCalc(day2input))
        assertEquals(900, p.diveCalcWithAim(day2input))

        println("\n== testDay03 ==")
        assertEquals(198, p.binaryDiagnostic(day3input))
        assertEquals(230, p.lifeSupportDiagnostic(day3input))
    }

    @Test
    fun testDay04() {
        println("\n== testDay04 ==")
        val p = Day04Bingo(debug = true)
        assertEquals(4512, p.bingoGame(day4input))
        assertEquals(1924, p.reverseBingo(day4input))
    }

    @Test
    fun testDay05() {
        println("\n== testDay05 ==")
        val p = Day05Vents(debug = true)
        assertEquals(5, p.overlappingVents(day5input, countDiagonals = false))
        assertEquals(12, p.overlappingVents(day5input, countDiagonals = true))
    }
}
