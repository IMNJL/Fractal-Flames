package backend.academy.fractall_flame.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class HSBGradientTest {

    private final HSBGradient gradient = new HSBGradient();

    @Test
    void testApply_withLowIterations_returnsExpectedColor() {
        double x = 0.5;
        double y = 0.5;
        int iterations = 10; // Низкое количество итераций

        Color color = gradient.apply(x, y, iterations);

        // Проверяем, что цвет не null
        assertNotNull(color);

        // Проверяем значение оттенка (hue), насыщенности (saturation) и яркости (brightness)
        assertTrue(color.getRGB() != Color.BLACK.getRGB(), "Color should not be black");

        // Проверка, что значения находятся в допустимом диапазоне для цвета HSB
        assertTrue(color.getRed() >= 0 && color.getRed() <= 255);
        assertTrue(color.getGreen() >= 0 && color.getGreen() <= 255);
        assertTrue(color.getBlue() >= 0 && color.getBlue() <= 255);
    }

    @Test
    void testApply_withHighIterations_returnsExpectedColor() {
        double x = 0.5;
        double y = 0.5;
        int iterations = 200; // Высокое количество итераций

        Color color = gradient.apply(x, y, iterations);

        // Проверяем, что цвет не null
        assertNotNull(color);

        // Проверяем, что значения находятся в допустимом диапазоне
        assertTrue(color.getRed() >= 0 && color.getRed() <= 255);
        assertTrue(color.getGreen() >= 0 && color.getGreen() <= 255);
        assertTrue(color.getBlue() >= 0 && color.getBlue() <= 255);
    }

    @Test
    void testApply_withZeroIterations_returnsExpectedColor() {
        double x = 0.5;
        double y = 0.5;
        int iterations = 0; // Ноль итераций

        Color color = gradient.apply(x, y, iterations);

        // Проверяем, что цвет не null
        assertNotNull(color);

        // Проверка, что значение цвета будет в пределах от 0 до 1 по компонентам HSB
        assertTrue(color.getRed() >= 0 && color.getRed() <= 255);
        assertTrue(color.getGreen() >= 0 && color.getGreen() <= 255);
        assertTrue(color.getBlue() >= 0 && color.getBlue() <= 255);
    }

    @Test
    void testApply_differentCoordinates_returnsDifferentColors() {
        // Проверяем, что разные координаты (x, y) приводят к разным цветам
        double x1 = 0.5, y1 = 0.5, x2 = 0.6, y2 = 0.7;
        int iterations = 100;

        Color color1 = gradient.apply(x1, y1, iterations);
        Color color2 = gradient.apply(x2, y2, iterations);

        assertNotEquals(color1.getRGB(), color2.getRGB(), "Colors should be different for different coordinates");
    }

    @Test
    void testApply_normalizedIterations() {
        double x = 0.5;
        double y = 0.5;
        int highIterations = 500;
        int lowIterations = 10;

        Color highColor = gradient.apply(x, y, highIterations);
        Color lowColor = gradient.apply(x, y, lowIterations);

        // Проверяем, что цвет для высоких и низких итераций отличается
        assertNotEquals(highColor.getRGB(), lowColor.getRGB(), "Color should change with different iterations");
    }
}
