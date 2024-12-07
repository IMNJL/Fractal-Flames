package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PDJTransformationTest {

    @Test
    void testApplyTransformation() {
        // Создаем экземпляр трансформации с некоторыми параметрами
        PDJTransformation transformation = new PDJTransformation(1.0, 2.0, 3.0, 4.0);

        // Тестовая точка
        Point point = new Point(1.0, 1.0);

        // Применяем трансформацию
        Point transformedPoint = transformation.apply(point);

        // Ожидаемые значения для newX и newY по формуле:
        // newX = sin(p1 * y) - cos(p2 * x) = sin(1.0 * 1.0) - cos(2.0 * 1.0)
        // newY = sin(p3 * x) - cos(p4 * y) = sin(3.0 * 1.0) - cos(4.0 * 1.0)
        double expectedX = Math.sin(1.0) - Math.cos(2.0);
        double expectedY = Math.sin(3.0) - Math.cos(4.0);

        // Проверяем, что трансформированные координаты верны
        assertEquals(expectedX, transformedPoint.x(), 1e-6); // допускаем небольшую погрешность
        assertEquals(expectedY, transformedPoint.y(), 1e-6);
    }

    @Test
    void testGetColor() {
        // Создаем экземпляр трансформации
        PDJTransformation transformation = new PDJTransformation(1.0, 2.0, 3.0, 4.0);

        // Проверяем, что цвет трансформации правильный
        assertEquals(Color.BLUE, transformation.getColor());
    }
}
