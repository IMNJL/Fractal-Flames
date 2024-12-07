package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;

@SuppressWarnings("PREDICTABLE_RANDOM")
public class SingleThreadFractalRenderer extends DefaultFractalRenderer implements Renderer {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); // Singleton

    public SingleThreadFractalRenderer(long seed) {
        super(seed);
    }

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
            Point point = randomPoint(world);
            while (point == null) {
                point = randomPoint(world);
            }
            double color = SECURE_RANDOM.nextDouble();

            for (int step = 0; step < iterPerSample; ++step) {
                Transformation transformation = variations.get(SECURE_RANDOM.nextInt(variations.size()));
                point = transformation.apply(point);
                color = (color + SECURE_RANDOM.nextDouble()) / 2.0;

                applySymmetryAndColor(canvas, world, point, symmetry, colorGradient);
            }
        }

        BrightnessNormalizer.normalize(canvas);
        return canvas;
    }
}
