package backend.academy.fractall_flame.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class DefaultColorGradientTest {

    @Test
    void testApply_withAnyCoordinatesAndIterations_returnsWhiteColor() {
        DefaultColorGradient gradient = new DefaultColorGradient();

        // Применяем градиент для произвольных координат и итераций
        Color result = gradient.apply(1.23, 4.56, 100);

        // Проверяем, что результат всегда белый цвет
        assertEquals(Color.WHITE, result, "Color should be white (RGB = 255, 255, 255)");
    }

    @Test
    void testApply_withZeroCoordinates_returnsWhiteColor() {
        DefaultColorGradient gradient = new DefaultColorGradient();

        // Применяем градиент для координат (0, 0) и произвольного количества итераций
        Color result = gradient.apply(0.0, 0.0, 50);

        // Проверяем, что результат всегда белый цвет
        assertEquals(Color.WHITE, result, "Color should be white (RGB = 255, 255, 255)");
    }

    @Test
    void testApply_withMaxIterations_returnsWhiteColor() {
        DefaultColorGradient gradient = new DefaultColorGradient();

        // Применяем градиент для произвольных координат с максимальным значением итераций
        Color result = gradient.apply(1.0, 1.0, Integer.MAX_VALUE);

        // Проверяем, что результат всегда белый цвет
        assertEquals(Color.WHITE, result, "Color should be white (RGB = 255, 255, 255)");
    }

    @Test
    void testApply_withNegativeCoordinates_returnsWhiteColor() {
        DefaultColorGradient gradient = new DefaultColorGradient();

        // Применяем градиент для отрицательных координат
        Color result = gradient.apply(-1.0, -1.0, 100);

        // Проверяем, что результат всегда белый цвет
        assertEquals(Color.WHITE, result, "Color should be white (RGB = 255, 255, 255)");
    }

    @Test
    void testApply_withLargeCoordinates_returnsWhiteColor() {
        DefaultColorGradient gradient = new DefaultColorGradient();

        // Применяем градиент для очень больших координат
        Color result = gradient.apply(1000000.0, 1000000.0, 100);

        // Проверяем, что результат всегда белый цвет
        assertEquals(Color.WHITE, result, "Color should be white (RGB = 255, 255, 255)");
    }
}
