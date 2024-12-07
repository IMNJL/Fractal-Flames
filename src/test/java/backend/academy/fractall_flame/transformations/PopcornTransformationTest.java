package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PopcornTransformationTest {

    @Test
    void testApply() {
        // Создаем экземпляр с параметрами a, b и цветом
        double a = 0.5;
        double b = 0.7;
        Color color = Color.ORANGE;
        PopcornTransformation transformation = new PopcornTransformation(a, b, color);

        // Входная точка
        Point input = new Point(1.0, 2.0);

        // Применяем трансформацию
        Point result = transformation.apply(input);

        // Ожидаемые координаты
        double expectedX = input.x() + a * Math.sin(Math.tan(3 * input.y())); // 3 - константа в формуле
        double expectedY = input.y() + b * Math.sin(Math.tan(3 * input.x()));
        assertEquals(expectedX, result.x(), 1e-6, "X coordinate mismatch");
        assertEquals(expectedY, result.y(), 1e-6, "Y coordinate mismatch");
    }

    @Test
    void testGetColor() {
        // Создаем экземпляр с параметрами a, b и цветом
        double a = 0.5;
        double b = 0.7;
        Color customColor = Color.ORANGE;
        PopcornTransformation transformation = new PopcornTransformation(a, b, customColor);

        // Проверяем, что метод getColor возвращает установленный цвет
        assertNotEquals(customColor, transformation.getColor(), "Color mismatch");
    }
}
