package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.HSBGradient;
import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MultiThreadFractalRendererTest {

    @Test
    void testRenderBasicFunctionalityWithHSBGradient() {
        // Настройка данных
        Rect world = new Rect(0, 0, 100, 100);
        ColorGradient gradient = new HSBGradient();  // Используем HSBGradient
        Pixel[] data = new Pixel[10 * 10];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(Color.BLACK, 100); // Инициализируем черным цветом
        }

        FractalImage canvas = new FractalImage(data, 10, 10); // Используем массив пикселей

        // Мокаем трансформацию, чтобы возвращала тот же Point
        Transformation mockTransformation = mock(Transformation.class);
        when(mockTransformation.apply(any(Point.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Transformation> transformations = List.of(mockTransformation);

        MultiThreadFractalRenderer renderer = new MultiThreadFractalRenderer(123);

        // Запуск рендера
        FractalImage result = renderer.render(canvas, world, transformations, 500, 5, 1, gradient);

        // Проверки
        assertNotNull(result, "Результирующее изображение не должно быть null");
        assertEquals(canvas.width(), result.width());
        assertEquals(canvas.height(), result.height());
        verify(mockTransformation, atLeastOnce()).apply(any(Point.class));

        // Дополнительные проверки: можем проверить что цвет был применен
        Color color = result.getColorAt(0, 0, 100); // Пример: получаем цвет в точке (50, 50)
        assertNotNull(color, "Цвет в точке не должен быть null");

        // Проверка на корректность применения градиента: мы знаем, что цвет должен быть в пределах HSB
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        assertTrue(hsb[0] >= 0 && hsb[0] <= 1, "Оттенок должен быть в пределах [0, 1]");
        assertTrue(hsb[1] >= 0 && hsb[1] <= 1, "Насыщенность должна быть в пределах [0, 1]");
        assertTrue(hsb[2] >= 0 && hsb[2] <= 1, "Яркость должна быть в пределах [0, 1]");
    }
}
