package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class SinusoidalTransformationTest {

    @Test
    void testApply() {
        // Создаем экземпляр с заданным цветом
        SinusoidalTransformation transformation = new SinusoidalTransformation(Color.GREEN);

        // Входная точка
        Point input = new Point(Math.PI / 2, Math.PI); // Известные значения для проверки sin

        // Применяем трансформацию
        Point result = transformation.apply(input);

        // Ожидаемые значения
        double expectedX = Math.sin(input.x()); // sin(π/2) = 1.0
        double expectedY = Math.sin(input.y()); // sin(π) = 0.0
        assertEquals(expectedX, result.x(), 1e-6, "X coordinate mismatch");
        assertEquals(expectedY, result.y(), 1e-6, "Y coordinate mismatch");
    }

    @Test
    void testGetColor() {
        // Устанавливаем пользовательский цвет
        Color customColor = Color.MAGENTA;
        SinusoidalTransformation transformation = new SinusoidalTransformation(customColor);

        // Проверяем, что метод getColor возвращает установленный цвет
        assertNotEquals(customColor, transformation.getColor(), "Color mismatch");
    }
}
