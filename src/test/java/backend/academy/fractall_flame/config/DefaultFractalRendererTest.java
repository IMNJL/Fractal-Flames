package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.DefaultFractalRenderer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultFractalRendererTest {

    @Test
    void testRandomPoint() {
        DefaultFractalRenderer renderer = new DefaultFractalRenderer(12345L);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);

        Point point = renderer.randomPoint(world);

        assertTrue(world.contains(point), "Random point should be within the world bounds");
    }

    @Test
    void testRotatePoint() {
        DefaultFractalRenderer renderer = new DefaultFractalRenderer(12345L);
        Point point = new Point(1.0, 0.0);

        Point rotated = renderer.rotate(point, Math.PI / 2); // Rotate 90 degrees

        assertEquals(0.0, rotated.x(), 1e-6, "Rotated point x should be close to 0");
        assertEquals(1.0, rotated.y(), 1e-6, "Rotated point y should be close to 1");
    }

    @Test
    void testMappingToCanvas() {
        DefaultFractalRenderer renderer = new DefaultFractalRenderer(12345L);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        Point point = new Point(0.0, 0.0);

        int canvasWidth = 100;
        int canvasHeight = 100;

        int x = renderer.mapToCanvasX(world, point, canvasWidth);
        int y = renderer.mapToCanvasY(world, point, canvasHeight);

        assertEquals(50, x, "Mapped x should be 50 for center point");
        assertEquals(50, y, "Mapped y should be 50 for center point");
    }
}
