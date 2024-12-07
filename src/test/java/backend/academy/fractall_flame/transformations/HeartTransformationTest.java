package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class HeartTransformationTest {

    @Test
    void testApply() {
        HeartTransformation transformation = new HeartTransformation();

        // Входная точка
        Point input = new Point(2.0, 3.0);

        // Применяем трансформацию
        Point result = transformation.apply(input);

        // Проверяем ожидаемый результат
        double expectedX = input.x() * input.x() - input.y() * input.y(); // x^2 - y^2
        double expectedY = 2 * input.x() * input.y(); // 2xy
        assertEquals(expectedX, result.x(), 1e-6, "X coordinate mismatch");
        assertEquals(expectedY, result.y(), 1e-6, "Y coordinate mismatch");
    }

    @Test
    void testGetColor() {
        HeartTransformation transformation = new HeartTransformation();

        // Проверяем, что цвет трансформации корректный
        assertEquals(Color.RED, transformation.getColor(), "Color mismatch");
    }
}
