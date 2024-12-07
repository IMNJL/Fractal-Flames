package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class LinearTransformationTest {

    @Test
    void testApplyTransformation() {
        // Создаем экземпляр трансформации
        LinearTransformation transformation = new LinearTransformation(1, 2, 3, 4);

        // Тестовая точка
        Point point = new Point(1.0, 1.0);

        // Применяем трансформацию
        Point transformedPoint = transformation.apply(point);

        // Ожидаем, что новые координаты будут (1*1 + 2*1, 3*1 + 4*1) = (3.0, 7.0)
        assertEquals(3.0, transformedPoint.x());
        assertEquals(7.0, transformedPoint.y());
    }

    @Test
    void testGetColor() {
        // Создаем экземпляр трансформации
        LinearTransformation transformation = new LinearTransformation(1, 2, 3, 4);

        // Проверяем, что цвет трансформации правильный
        assertEquals(Color.BLUE, transformation.getColor());
    }
}
