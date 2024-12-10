package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.BrightnessNormalizer;
import backend.academy.fractall_flame.processing.DefaultFractalRenderer;
import backend.academy.fractall_flame.processing.FractalImage;
import backend.academy.fractall_flame.transformations.Transformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DefaultFractalRendererTest {

    @BeforeEach
    void setUp() {
        render();
    }

    DefaultFractalRenderer render() {
        return new DefaultFractalRenderer(12345L) {
            @Override
            public FractalImage render(
                FractalImage canvas,
                Rect world,
                List<Transformation> variations,
                int samples,
                int iterPerSample,
                int symmetry,
                ColorGradient colorGradient
            ) {
                for (int num = 0; num < samples; ++num) {
                    Point point = randomPoint(world, secureRandom);
                    double color = secureRandom.nextDouble();
                    for (int step = 0; step < iterPerSample; ++step) {
                        Transformation transformation = variations.get(secureRandom.nextInt(variations.size()));
                        point = transformation.apply(point);
                        color = (color + secureRandom.nextDouble()) / 2.0;

                        applySymmetryAndColor(canvas, world, point, symmetry, colorGradient);
                    }
                }

                BrightnessNormalizer.normalize(canvas);
                return canvas;
            }
        };
    }

    @Test
    void testRandomPoint() {

        SecureRandom SecureRandom = new SecureRandom();

        DefaultFractalRenderer renderer = render();

        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);

        Point point = renderer.randomPoint(world, SecureRandom);

        assertTrue(world.contains(point), "Random point should be within the world bounds");
    }

    @Test
    void testRotatePoint() {
        DefaultFractalRenderer renderer = render();
        Point point = new Point(1.0, 0.0);

        Point rotated = renderer.rotate(point, Math.PI / 2); // Rotate 90 degrees

        assertEquals(0.0, rotated.x(), 1e-6, "Rotated point x should be close to 0");
        assertEquals(1.0, rotated.y(), 1e-6, "Rotated point y should be close to 1");
    }

    @Test
    void testMappingToCanvas() {
        DefaultFractalRenderer renderer = render();
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
