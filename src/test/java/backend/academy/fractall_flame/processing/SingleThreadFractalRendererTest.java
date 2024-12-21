package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.DefaultColorGradient;
import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SingleThreadFractalRendererTest {

    private DefaultFractalRenderer renderer;
    private Rect world;
    private FractalImage canvas;
    private List<Transformation> variations;
    private ColorGradient colorGradient;

    @BeforeEach
    void setUp() {
        renderer = new SingleThreadFractalRenderer(12345L);
        world = mock(Rect.class);
        canvas = mock(FractalImage.class);
        variations = new ArrayList<>();
        colorGradient = mock(ColorGradient.class);
    }
    @Test
    void testProcessing() {
        when(world.contains(any(Point.class))).thenReturn(true);
        when(canvas.contains(anyInt(), anyInt())).thenReturn(true);
        when(canvas.pixel(anyInt(), anyInt())).thenReturn(mock(Pixel.class));
        when(colorGradient.apply(anyDouble(), anyDouble(), anyInt())).thenReturn(Color.RED);

        Transformation transformation = mock(Transformation.class);
        when(transformation.apply(any(Point.class))).thenReturn(new Point(50.0, 50.0));
        variations.add(transformation);


        renderer.processing(canvas, world, variations, 1, 4, colorGradient, 0, 10);

        verify(canvas, atLeastOnce()).setPixel(anyInt(), anyInt(), any(Pixel.class));
    }
}
