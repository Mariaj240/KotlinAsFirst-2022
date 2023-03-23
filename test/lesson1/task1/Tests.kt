package lesson1.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.math.PI

class Tests {
    @Test
    @Tag("Example")
    fun sqr() {
        assertEquals(0, lesson3.task1.task1.sqr(0))
        assertEquals(4, lesson3.task1.task1.sqr(2))
        assertEquals(9, lesson3.task1.task1.sqr(-3))
    }

    @Test
    @Tag("Example")
    fun sqrDouble() {
        assertEquals(0.0, lesson3.task1.task1.sqr(0.0), 1e-13)
        assertEquals(4.0, lesson3.task1.task1.sqr(2.0), 1e-13)
        assertEquals(9.0, lesson3.task1.task1.sqr(-3.0), 1e-13)
    }

    @Test
    @Tag("Example")
    fun discriminant() {
        assertEquals(0.0, lesson3.task1.task1.discriminant(0.0, 0.0, 0.0), 1e-13)
        assertEquals(0.0, lesson3.task1.task1.discriminant(1.0, -2.0, 1.0), 1e-13)
        assertEquals(1.0, lesson3.task1.task1.discriminant(1.0, 3.0, 2.0), 1e-13)
    }

    @Test
    @Tag("Example")
    fun quadraticEquationRoot() {
        assertEquals(2.0, lesson3.task1.task1.quadraticEquationRoot(1.0, -3.0, 2.0), 1e-13)
        assertEquals(1.0, lesson3.task1.task1.quadraticEquationRoot(1.0, -2.0, 1.0), 1e-13)
        assertEquals(-3.0, lesson3.task1.task1.quadraticEquationRoot(1.0, 6.0, 9.0), 1e-13)
    }

    @Test
    @Tag("Example")
    fun quadraticRootProduct() {
        assertEquals(1.0, lesson3.task1.task1.quadraticRootProduct(1.0, -2.0, 1.0), 1e-13)
        assertEquals(9.0, lesson3.task1.task1.quadraticRootProduct(1.0, 6.0, 9.0), 1e-13)
        assertEquals(2.0, lesson3.task1.task1.quadraticRootProduct(1.0, 3.0, 2.0), 1e-13)
    }

    @Test
    @Tag("3")
    fun seconds() {
        assertEquals(30035, lesson3.task1.task1.seconds(8, 20, 35))
        assertEquals(86400, lesson3.task1.task1.seconds(24, 0, 0))
        assertEquals(13, lesson3.task1.task1.seconds(0, 0, 13))
    }

    @Test
    @Tag("1")
    fun lengthInMeters() {
        assertEquals(18.98, lesson3.task1.task1.lengthInMeters(8, 2, 11), 1e-2)
        assertEquals(2.13, lesson3.task1.task1.lengthInMeters(1, 0, 0), 1e-2)
    }

    @Test
    @Tag("1")
    fun angleInRadian() {
        assertEquals(0.63256, lesson3.task1.task1.angleInRadian(36, 14, 35), 1e-5)
        assertEquals(PI / 2.0, lesson3.task1.task1.angleInRadian(90, 0, 0), 1e-5)
    }

    @Test
    @Tag("1")
    fun trackLength() {
        assertEquals(5.0, lesson3.task1.task1.trackLength(3.0, 0.0, 0.0, 4.0), 1e-5)
        assertEquals(1.0, lesson3.task1.task1.trackLength(0.0, 1.0, -1.0, 1.0), 1e-5)
        assertEquals(1.41, lesson3.task1.task1.trackLength(1.0, 1.0, 2.0, 2.0), 1e-2)
    }

    @Test
    @Tag("2")
    fun thirdDigit() {
        assertEquals(8, lesson3.task1.task1.thirdDigit(3801))
        assertEquals(1, lesson3.task1.task1.thirdDigit(100))
        assertEquals(0, lesson3.task1.task1.thirdDigit(1000))
    }

    @Test
    @Tag("2")
    fun travelMinutes() {
        assertEquals(216, lesson3.task1.task1.travelMinutes(9, 25, 13, 1))
        assertEquals(1, lesson3.task1.task1.travelMinutes(21, 59, 22, 0))
    }

    @Test
    @Tag("2")
    fun accountInThreeYears() {
        assertEquals(133.1, lesson3.task1.task1.accountInThreeYears(100, 10), 1e-2)
        assertEquals(1.0, lesson3.task1.task1.accountInThreeYears(1, 0), 1e-2)
        assertEquals(104.0, lesson3.task1.task1.accountInThreeYears(13, 100), 1e-2)
    }

    @Test
    @Tag("2")
    fun numberRevert() {
        assertEquals(874, lesson3.task1.task1.numberRevert(478))
        assertEquals(201, lesson3.task1.task1.numberRevert(102))
    }
}