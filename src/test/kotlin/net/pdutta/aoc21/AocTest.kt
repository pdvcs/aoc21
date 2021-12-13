package net.pdutta.aoc21

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AoCTest {
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

    @Test
    fun testDay01() {
        println("== testDay01 ==")
        assertEquals(7, measureSweep(day1input))
        assertEquals(5, measureSweepWithSlidingWindow(day1input))
    }

    @Test
    fun testDay02() {
        println("== testDay02 ==")
        assertEquals(150, diveCalc(day2input))
        assertEquals(900, diveCalcWithAim(day2input))
    }

    @Test
    fun testDay03() {
        println("== testDay03 ==")
        assertEquals(198, binaryDiagnostic(day3input))
        assertEquals(230, lifeSupportDiagnostic(day3input))
    }

    @Test
    fun testDay04() {
        println("== testDay04 ==")
        assertEquals(4512, bingoGame(day4input))
        assertEquals(1924, reverseBingo(day4input))
    }
}
