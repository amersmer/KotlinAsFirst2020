package lesson8.task1

import lesson8.task1.Direction.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class HexTests {

    @Test
    @Tag("3")
    fun hexPointDistance() {
        assertEquals(5, HexPoint(6, 1).distance(HexPoint(1, 4)))
        assertEquals(5, HexPoint(0, 5).distance(HexPoint(0, 0)))
        assertEquals(5, HexPoint(5, 0).distance(HexPoint(0, 0)))
        assertEquals(0, HexPoint(0, 0).distance(HexPoint(0, 0)))
    }

    @Test
    @Tag("3")
    fun hexagonDistance() {
        assertEquals(2, Hexagon(HexPoint(1, 3), 1).distance(Hexagon(HexPoint(6, 2), 2)))
        assertEquals(0, Hexagon(HexPoint(-999, -237), 310).distance(Hexagon(HexPoint(-558, -999), 876)))
        assertEquals(0, Hexagon(HexPoint(0, 0), 310).distance(Hexagon(HexPoint(1, 1), 876)))
    }

    @Test
    @Tag("1")
    fun hexagonContains() {
        assertTrue(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(2, 3)))
        assertFalse(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(4, 4)))
    }

    @Test
    @Tag("2")
    fun hexSegmentValid() {
        assertTrue(HexSegment(HexPoint(1, 3), HexPoint(5, 3)).isValid())
        assertTrue(HexSegment(HexPoint(3, 1), HexPoint(3, 6)).isValid())
        assertTrue(HexSegment(HexPoint(1, 5), HexPoint(4, 2)).isValid())
        assertFalse(HexSegment(HexPoint(3, 1), HexPoint(6, 2)).isValid())
        assertFalse(HexSegment(HexPoint(5, 0), HexPoint(6, 1)).isValid())
        // Нулевой отрезок параллелен ничему
        assertFalse(HexSegment(HexPoint(1, 1), HexPoint(1, 1)).isValid())
        assertFalse(HexSegment(HexPoint(-999, -999), HexPoint(-999, -999)).isValid())
        assertFalse(HexSegment(HexPoint(-999, -999), HexPoint(-1000, -1000)).isValid())
    }

    @Test
    @Tag("3")
    fun hexSegmentDirection() {
        assertEquals(RIGHT, HexSegment(HexPoint(1, 3), HexPoint(5, 3)).direction())
        assertEquals(UP_RIGHT, HexSegment(HexPoint(3, 1), HexPoint(3, 6)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(1, 5), HexPoint(4, 2)).direction())
        assertEquals(LEFT, HexSegment(HexPoint(5, 3), HexPoint(1, 3)).direction())
        assertEquals(DOWN_LEFT, HexSegment(HexPoint(3, 6), HexPoint(3, 1)).direction())
        assertEquals(UP_LEFT, HexSegment(HexPoint(4, 2), HexPoint(1, 5)).direction())
        assertEquals(INCORRECT, HexSegment(HexPoint(3, 1), HexPoint(6, 2)).direction())
        // Мои тесты
        assertEquals(RIGHT, HexSegment(HexPoint(4, 2), HexPoint(7, 2)).direction())
        assertEquals(UP_RIGHT, HexSegment(HexPoint(4, 1), HexPoint(4, 5)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(3, 5), HexPoint(8, 0)).direction())
        assertEquals(LEFT, HexSegment(HexPoint(4, 6), HexPoint(1, 6)).direction())
        assertEquals(DOWN_LEFT, HexSegment(HexPoint(5, 3), HexPoint(5, 2)).direction())
        assertEquals(UP_LEFT, HexSegment(HexPoint(6, 1), HexPoint(4, 3)).direction())
        assertEquals(INCORRECT, HexSegment(HexPoint(7, 4), HexPoint(2, 2)).direction())
        // Дополнительные тесты, с учетом наличия отрицательных чисел
        assertEquals(LEFT, HexSegment(HexPoint(0, -1), HexPoint(-1, -1)).direction())
        assertEquals(RIGHT, HexSegment(HexPoint(0, -1), HexPoint(1, -1)).direction())
        assertEquals(LEFT, HexSegment(HexPoint(-2, -1), HexPoint(-5, -1)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(2, -1), HexPoint(3, -2)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(0, 1), HexPoint(3, -2)).direction())
        assertEquals(DOWN_LEFT, HexSegment(HexPoint(0, 7), HexPoint(0, -22)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(-1, 0), HexPoint(0, -1)).direction())
        // Тесты из котоеда
        assertEquals(INCORRECT, HexSegment(HexPoint(-1000, -557), HexPoint(-1000, -557)).direction())
        assertEquals(INCORRECT, HexSegment(HexPoint(-999, -999), HexPoint(-558, -558)).direction())
    }

    @Test
    @Tag("2")
    fun oppositeDirection() {
        assertEquals(LEFT, RIGHT.opposite())
        assertEquals(DOWN_LEFT, UP_RIGHT.opposite())
        assertEquals(UP_LEFT, DOWN_RIGHT.opposite())
        assertEquals(RIGHT, LEFT.opposite())
        assertEquals(DOWN_RIGHT, UP_LEFT.opposite())
        assertEquals(UP_RIGHT, DOWN_LEFT.opposite())
        assertEquals(INCORRECT, INCORRECT.opposite())
    }

    @Test
    @Tag("3")
    fun nextDirection() {
        assertEquals(UP_RIGHT, RIGHT.next())
        assertEquals(UP_LEFT, UP_RIGHT.next())
        assertEquals(RIGHT, DOWN_RIGHT.next())
        assertEquals(DOWN_LEFT, LEFT.next())
        assertEquals(LEFT, UP_LEFT.next())
        assertEquals(DOWN_RIGHT, DOWN_LEFT.next())
        assertThrows(IllegalArgumentException::class.java) {
            INCORRECT.next()
        }
    }

    @Test
    @Tag("2")
    fun isParallelDirection() {
        assertTrue(RIGHT.isParallel(RIGHT))
        assertTrue(RIGHT.isParallel(LEFT))
        assertFalse(RIGHT.isParallel(UP_LEFT))
        assertFalse(RIGHT.isParallel(INCORRECT))
        assertTrue(UP_RIGHT.isParallel(UP_RIGHT))
        assertTrue(UP_RIGHT.isParallel(DOWN_LEFT))
        assertFalse(UP_RIGHT.isParallel(UP_LEFT))
        assertFalse(INCORRECT.isParallel(INCORRECT))
        assertFalse(INCORRECT.isParallel(UP_LEFT))
    }

    @Test
    @Tag("3")
    fun hexPointMove() {
        assertEquals(HexPoint(3, 3), HexPoint(0, 3).move(RIGHT, 3))
        assertEquals(HexPoint(3, 5), HexPoint(5, 3).move(UP_LEFT, 2))
        assertEquals(HexPoint(5, 0), HexPoint(5, 4).move(DOWN_LEFT, 4))
        assertEquals(HexPoint(1, 1), HexPoint(1, 1).move(DOWN_RIGHT, 0))
        assertEquals(HexPoint(4, 2), HexPoint(2, 2).move(LEFT, -2))
        assertThrows(IllegalArgumentException::class.java) {
            HexPoint(0, 0).move(INCORRECT, 0)
        }
        assertEquals(HexPoint(5, 5), HexPoint(5, 3).move(DOWN_LEFT, -2))
    }

    @Test
    @Tag("5")
    fun pathBetweenHexes() {
        assertEquals(
            5, pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3)).size
        )
        assertEquals(
            3, pathBetweenHexes(HexPoint(y = 0, x = 0), HexPoint(y = 1, x = 1)).size
        )
        assertEquals(
            3, pathBetweenHexes(HexPoint(y = 1, x = 1), HexPoint(y = 0, x = 0)).size
        )
        assertEquals(
            5, pathBetweenHexes(HexPoint(y = 5, x = 3), HexPoint(y = 2, x = 2)).size
        )
        assertEquals(
            4, pathBetweenHexes(HexPoint(y = 0, x = 0), HexPoint(y = 2, x = 1)).size
        )
        assertEquals(
            9, pathBetweenHexes(HexPoint(y = 0, x = 0), HexPoint(y = 4, x = 4)).size
        )
        assertEquals(
            6, pathBetweenHexes(HexPoint(y = 0, x = 0), HexPoint(y = 4, x = 1)).size
        )
        assertEquals(
            1, pathBetweenHexes(HexPoint(y = 0, x = 0), HexPoint(y = 0, x = 0)).size
        )
    }

    @Test
    @Tag("20")
    fun hexagonByThreePoints() {
        assertEquals(
            Hexagon(HexPoint(5, 2), 4),
            hexagonByThreePoints(HexPoint(1, 2), HexPoint(9, 0), HexPoint(3, 6))
        )
        assertNull(
            hexagonByThreePoints(HexPoint(1, 2), HexPoint(8, 0), HexPoint(3, 6))
        )
        assertEquals(
            Hexagon(HexPoint(4, 2), 2),
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(4, 4))
        )
        assertNull(
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(5, 4))
        )
        assertEquals(
            3,
            hexagonByThreePoints(HexPoint(2, 3), HexPoint(3, 3), HexPoint(5, 3))?.radius
        )
        assertEquals(
            Hexagon(HexPoint(0, 0), 0),
            hexagonByThreePoints(HexPoint(0, 0), HexPoint(0, 0), HexPoint(0, 0))
        )
        // Проверка, что в теории он не очень долго будет считать
        assertNull(
            hexagonByThreePoints(HexPoint(2000, 3), HexPoint(3000, 3000), HexPoint(5000, 3009))
        )
        assertEquals(
            Hexagon(HexPoint(5000, 0), 3000),
            hexagonByThreePoints(HexPoint(2000, 3), HexPoint(3000, 3000), HexPoint(5000, 3000))
        )
    }

    @Test
    @Tag("20")
    fun minContainingHexagon() {
        val points = arrayOf(HexPoint(3, 1), HexPoint(3, 2), HexPoint(5, 4), HexPoint(8, 1))
        val result = minContainingHexagon(*points)
        assertEquals(3, result.radius)
        assertTrue(points.all { result.contains(it) })
    }

    @Test
    fun radiusBoundary() {
        assertEquals(
            listOf<HexPoint>(),
            listOf(
                HexPoint(6, 4),
                HexPoint(5, 4),
                HexPoint(5, 3),
                HexPoint(6, 2),
                HexPoint(7, 2),
                HexPoint(7, 3)
            ) - (Hexagon(HexPoint(6, 3), 1).radiusBoundary())
        )
        assertEquals(
            listOf<HexPoint>(),
            listOf(
                HexPoint(6, 5),
                HexPoint(5, 5),
                HexPoint(4, 5),
                HexPoint(4, 4),
                HexPoint(4, 3),
                HexPoint(5, 2),
                HexPoint(6, 1),
                HexPoint(7, 1),
                HexPoint(8, 1),
                HexPoint(8, 2),
                HexPoint(8, 3),
                HexPoint(7, 4)
            ) - (Hexagon(HexPoint(6, 3), 2).radiusBoundary())
        )
        assertEquals(
            54,
            Hexagon(HexPoint(6, 3), 9).radiusBoundary().size
        )
    }
}