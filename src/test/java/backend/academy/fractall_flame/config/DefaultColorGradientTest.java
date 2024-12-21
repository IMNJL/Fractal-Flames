package backend.academy.fractall_flame.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class DefaultColorGradientTest {

    @Test
    void testDefaultColorGradient() {
        ColorGradient gradient = new DefaultColorGradient();

        Color color = gradient.apply(0.5, 0.5, 100);
        assertEquals(new Color(255, 255, 255), color);

        // другой цвет
        ColorGradient customGradient = new DefaultColorGradient(200, 100, 50);
        Color customColor = customGradient.apply(0.5, 0.5, 50);
        assertNotNull(customColor);
    }
}
