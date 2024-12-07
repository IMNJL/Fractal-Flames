package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BrightnessNormalizerTest {

    private FractalImage image;
    private static final int IMAGE_WIDTH = 3;
    private static final int IMAGE_HEIGHT = 3;

    @BeforeEach
    void setUp() {
        // Создаем мок изображения с размерами 3x3
        image = Mockito.mock(FractalImage.class);
        Mockito.when(image.width()).thenReturn(IMAGE_WIDTH);
        Mockito.when(image.height()).thenReturn(IMAGE_HEIGHT);

        // Настроим пиксели изображения с различными значениями hitCount
        Mockito.when(image.pixel(0, 0)).thenReturn(new Pixel(255, 255, 255, 10));
        Mockito.when(image.pixel(0, 1)).thenReturn(new Pixel(255, 255, 255, 20));
        Mockito.when(image.pixel(0, 2)).thenReturn(new Pixel(255, 255, 255, 30));
        Mockito.when(image.pixel(1, 0)).thenReturn(new Pixel(255, 255, 255, 40));
        Mockito.when(image.pixel(1, 1)).thenReturn(new Pixel(255, 255, 255, 50));
        Mockito.when(image.pixel(1, 2)).thenReturn(new Pixel(255, 255, 255, 60));
        Mockito.when(image.pixel(2, 0)).thenReturn(new Pixel(255, 255, 255, 70));
        Mockito.when(image.pixel(2, 1)).thenReturn(new Pixel(255, 255, 255, 80));
        Mockito.when(image.pixel(2, 2)).thenReturn(new Pixel(255, 255, 255, 90));
    }

    @Test
    void testGetMaxHitCount() {
        int maxHitCount = BrightnessNormalizer.getMaxHitCount(image);

        // Максимальное значение hitCount на изображении должно быть 90
        assertEquals(90, maxHitCount);
    }

    @Test
    void testNormalizeNoPixels() {
        // Создаем пустое изображение (размер 0x0)
        FractalImage emptyImage = Mockito.mock(FractalImage.class);
        Mockito.when(emptyImage.width()).thenReturn(0);
        Mockito.when(emptyImage.height()).thenReturn(0);

        // Пытаемся нормализовать пустое изображение
        BrightnessNormalizer.normalize(emptyImage);

        // Убедимся, что для пустого изображения ничего не происходит (проверка вызова методов)
        Mockito.verify(emptyImage, Mockito.never()).setPixel(Mockito.anyInt(), Mockito.anyInt(), Mockito.any());
    }
}
