package backend.academy.fractall_flame.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    @Test
    void testPixelConstructor_withColor_returnsCorrectPixel() {
        Color color = Color.RED; // Пример: красный цвет
        int hitCount = 10;

        // Создаем Pixel
        Pixel pixel = new Pixel(color, hitCount);

        // Проверяем, что значения правильно инициализированы
        assertEquals(color.getRed(), pixel.r(), "Red component should match");
        assertEquals(color.getGreen(), pixel.g(), "Green component should match");
        assertEquals(color.getBlue(), pixel.b(), "Blue component should match");
        assertEquals(hitCount, pixel.hitCount(), "Hit count should match");
    }

    @Test
    void testPixelConstructor_withDifferentColor_returnsCorrectPixel() {
        Color color = new Color(100, 150, 200); // Пример: пользовательский цвет
        int hitCount = 5;

        // Создаем Pixel
        Pixel pixel = new Pixel(color, hitCount);

        // Проверяем, что значения правильно инициализированы
        assertEquals(color.getRed(), pixel.r(), "Red component should match");
        assertEquals(color.getGreen(), pixel.g(), "Green component should match");
        assertEquals(color.getBlue(), pixel.b(), "Blue component should match");
        assertEquals(hitCount, pixel.hitCount(), "Hit count should match");
    }

    @Test
    void testPixelRecord_withNegativeHitCount() {
        Color color = Color.GREEN;
        int hitCount = -1;

        // Создаем Pixel с отрицательным hitCount
        Pixel pixel = new Pixel(color, hitCount);

        // Проверяем, что отрицательный hitCount хранится корректно
        assertEquals(hitCount, pixel.hitCount(), "Hit count should be stored correctly, even if negative");
    }

    @Test
    void testPixelEquality_withSameValues() {
        Color color = Color.BLUE;
        int hitCount = 15;

        Pixel pixel1 = new Pixel(color, hitCount);
        Pixel pixel2 = new Pixel(color, hitCount);

        // Проверяем, что два объекта Pixel с одинаковыми значениями равны
        assertEquals(pixel1, pixel2, "Pixels with the same values should be equal");
    }

    @Test
    void testPixelInequality_withDifferentValues() {
        Color color1 = Color.RED;
        int hitCount1 = 10;

        Color color2 = Color.GREEN;
        int hitCount2 = 20;

        Pixel pixel1 = new Pixel(color1, hitCount1);
        Pixel pixel2 = new Pixel(color2, hitCount2);

        // Проверяем, что два объекта Pixel с разными значениями не равны
        assertNotEquals(pixel1, pixel2, "Pixels with different values should not be equal");
    }
}
