package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class HorseshoeTransformationTest {

    @Test
    void testApply() {
        HorseshoeTransformation transformation = new HorseshoeTransformation();

        // Входная точка
        Point input = new Point(1.5, -2.0);

        // Применяем трансформацию
        Point result = transformation.apply(input);

        // Ожидаемые координаты
        double expectedX = (input.x() - input.y()) * (input.x() + input.y()); // (x - y)(x + y)
        double expectedY = 2 * input.x() * input.y(); // 2xy
        assertEquals(expectedX, result.x(), 1e-6, "X coordinate mismatch");
        assertEquals(expectedY, result.y(), 1e-6, "Y coordinate mismatch");
    }

    @Test
    void testGetColor() {
        HorseshoeTransformation transformation = new HorseshoeTransformation();

        // Проверяем, что цвет трансформации соответствует ожидаемому
        assertEquals(Color.BLUE, transformation.getColor(), "Color mismatch");
    }
}
