package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SingleThreadFractalRendererTest {

    private FractalImage canvas;
    private Rect world;
    private List<Transformation> transformations;
    private ColorGradient colorGradient;
    private SingleThreadFractalRenderer renderer;

    @BeforeEach
    void setUp() {
        canvas = Mockito.mock(FractalImage.class);
        world = new Rect(-1.0, -1.0, 2.0, 2.0);  // Пример области мира
        transformations = Arrays.asList(
            Mockito.mock(Transformation.class),
            Mockito.mock(Transformation.class)
        );
        colorGradient = Mockito.mock(ColorGradient.class);

        renderer = new SingleThreadFractalRenderer(12345L);
    }

    @Test
    void testRandomPointGeneration() {

        SecureRandom secureRandom = new SecureRandom();
        // Создаем экземпляр renderer
        SingleThreadFractalRenderer renderer = new SingleThreadFractalRenderer(12345L);

        // Генерация случайной точки для переданного мира
        Point randomPoint = renderer.randomPoint(world, secureRandom);

        // Проверяем, что точка лежит внутри заданного мира
        assertTrue(world.contains(randomPoint));
    }

    @Test
    void testRenderWithEmptyTransformations() {
        // Пытаемся вызвать render с пустым списком трансформаций
        List<Transformation> emptyTransformations = Arrays.asList();
        assertThrows(IllegalArgumentException.class, () -> {
            renderer.render(canvas, world, emptyTransformations, 10, 100, 4, colorGradient);
        });
    }

    @Test
    void testRenderWithNullCanvas() {
        // Пытаемся вызвать render с null канвасом
        assertThrows(NullPointerException.class, () -> {
            renderer.render(null, world, transformations, 10, 100, 4, colorGradient);
        });
    }

    @Test
    void testChooseTransformation() {
        // Проверка, что метод случайным образом выбирает трансформацию
        Point point = new Point(0.0, 0.0);
        Transformation transformation = transformations.get(0);
        when(transformation.apply(point)).thenReturn(new Point(1.0, 1.0));

        // Применяем трансформацию
        Point newPoint = transformation.apply(point);
        assertEquals(new Point(1.0, 1.0), newPoint);
    }
}
