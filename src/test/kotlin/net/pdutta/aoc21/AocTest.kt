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
                            """.trimIndent()
    private val day3input = "00100 11110 10110 10111 10101 01111 00111 11100 10000 11001 00010 01010".split(" ")

    @Test
    fun testDay01() {
        println("== testDay01 ==")
        assertEquals(7, measureSweep(day1input))
        assertEquals(5, measureSweepWithSlidingWindow(day1input))
    }

    @Test
    fun testDay02() {
        println("== testDay02 ==")
        assertEquals(150, diveCalc(day2input.split("\n")))
        assertEquals(900, diveCalcWithAim(day2input.split("\n")))
    }

    @Test
    fun testDay03() {
        println("== testDay03 ==")
        assertEquals(198, binaryDiagnostic(day3input))
        assertEquals(230, lifeSupportDiagnostic(day3input))
    }

}
