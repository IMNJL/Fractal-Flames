package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.HeartTransformation;
import backend.academy.fractall_flame.transformations.Transformation;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testConfigDimensions() {
        assertEquals(1920, Config.WIDTH, "Width should be 1920");
        assertEquals(1080, Config.HEIGHT, "Height should be 1080");
    }

    @Test
    void testConfigSamples() {
        assertEquals(200_000, Config.SAMPLES, "Samples should be 200,000");
        assertEquals(150, Config.ITER_PER_SAMPLE, "Iterations per sample should be 150");
    }

    @Test
    void testConfigSymmetry() {
        assertEquals(4, Config.SYMMETRY, "Symmetry should be 4");
    }

    @Test
    void testConfigWorld() {
        Rect world = Config.WORLD;
        assertEquals(-1.2, world.x(), "World x should be -1.2");
        assertEquals(-1.5, world.y(), "World y should be -1.5");
        assertEquals(2.4, world.width(), "World width should be 2.4");
        assertEquals(3.0, world.height(), "World height should be 3.0");
    }

    @Test
    void testConfigColorPalette() {
        Color[] palette = Config.COLOR_PALETTE;
        assertEquals(9, palette.length, "Color palette should contain 9 colors");
        assertTrue(List.of(palette).contains(Color.RED), "Palette should contain Color.RED");
    }

    @Test
    void testConfigTransformations() {
        List<Transformation> transformations = Config.TRANSFORMATIONS;
        assertEquals(5, transformations.size(), "There should be 5 transformations");
        assertTrue(
            transformations.stream().anyMatch(t -> t instanceof HeartTransformation),
            "Transformations should include HeartTransformation"
        );
    }
}
