package backend.academy.fractall_flame.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testPointConstructor_withValidValues_returnsCorrectPoint() {
        double x = 1.23;
        double y = 4.56;

        // Создаем объект Point
        Point point = new Point(x, y);

        // Проверяем, что поля x и y установлены правильно
        assertEquals(x, point.x(), "X coordinate should match");
        assertEquals(y, point.y(), "Y coordinate should match");
    }

    @Test
    void testPointEquality_withSameValues() {
        double x = 1.23;
        double y = 4.56;

        Point point1 = new Point(x, y);
        Point point2 = new Point(x, y);

        // Проверяем, что два объекта Point с одинаковыми значениями равны
        assertEquals(point1, point2, "Points with the same values should be equal");
    }

    @Test
    void testPointEquality_withDifferentValues() {
        Point point1 = new Point(1.23, 4.56);
        Point point2 = new Point(2.34, 5.67);

        // Проверяем, что два объекта Point с разными значениями не равны
        assertNotEquals(point1, point2, "Points with different values should not be equal");
    }

    @Test
    void testPointHashCode_withEqualPoints() {
        Point point1 = new Point(1.23, 4.56);
        Point point2 = new Point(1.23, 4.56);

        // Проверяем, что одинаковые точки имеют одинаковый hashCode
        assertEquals(point1.hashCode(), point2.hashCode(), "Equal points should have the same hashCode");
    }

    @Test
    void testPointHashCode_withDifferentPoints() {
        Point point1 = new Point(1.23, 4.56);
        Point point2 = new Point(2.34, 5.67);

        // Проверяем, что разные точки имеют разные hashCode
        assertNotEquals(point1.hashCode(), point2.hashCode(), "Different points should have different hashCodes");
    }

    @Test
    void testPointWithZeroValues() {
        Point point = new Point(0.0, 0.0);

        // Проверяем, что точки с нулевыми значениями корректно создаются
        assertEquals(0.0, point.x(), "X coordinate should be 0.0");
        assertEquals(0.0, point.y(), "Y coordinate should be 0.0");
    }
}
