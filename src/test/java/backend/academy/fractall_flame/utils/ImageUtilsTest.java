package backend.academy.fractall_flame.utils;

import backend.academy.fractall_flame.processing.FractalImage;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {

    @Test
    void testImageSave() {
        FractalImage canvas = FractalImage.create(100, 100);
        Path path = Path.of("test_output.png");

        ImageUtils.save(canvas, path, ImageFormat.PNG);
        assertTrue(path.toFile().exists(), "Image file should be created");
        path.toFile().delete(); // Cleanup
    }
}
