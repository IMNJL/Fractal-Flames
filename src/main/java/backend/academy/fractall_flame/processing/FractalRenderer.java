package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.List;

@SuppressWarnings("PREDICTABLE_RANDOM")
public class FractalRenderer {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); // Singleton

    public FractalRenderer(long seed) {
        SECURE_RANDOM.setSeed(seed);
    }

    public void render(
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
            double color = SECURE_RANDOM.nextDouble();

            for (int step = 0; step < iterPerSample; ++step) {
                Transformation transformation = variations.get(SECURE_RANDOM.nextInt(variations.size()));
                point = transformation.apply(point);
                color = (color + SECURE_RANDOM.nextDouble()) / 2.0;

                applySymmetryAndColor(canvas, world, point, symmetry, colorGradient);
            }
        }

        BrightnessNormalizer.normalize(canvas);
    }

    private Point randomPoint(Rect world) {
        double x = world.x() + SECURE_RANDOM.nextDouble() * world.width();
        double y = world.y() + SECURE_RANDOM.nextDouble() * world.height();
        return new Point(x, y);
    }

    private void applySymmetryAndColor(
        FractalImage canvas, Rect world, Point point, int symmetry, ColorGradient colorGradient
    ) {
        double angleStep = Math.PI * 2 / symmetry;
        for (int i = 0; i < symmetry; i++) {
            Point rotated = rotate(point, i * angleStep);

            if (!world.contains(rotated)) {
                continue;
            }

            int x = mapToCanvasX(world, rotated, canvas.width());
            int y = mapToCanvasY(world, rotated, canvas.height());

            if (canvas.contains(x, y)) {
                int iterations = canvas.pixel(x, y).hitCount() + 1;
                Color pixelColor = colorGradient.apply(rotated.x(), rotated.y(), iterations);
                canvas.setPixel(x, y, new Pixel(
                    pixelColor.getRed(),
                    pixelColor.getGreen(),
                    pixelColor.getBlue(),
                    iterations
                ));
            }
        }
    }

    private Point rotate(Point p, double angle) {
        double x = p.x() * Math.cos(angle) - p.y() * Math.sin(angle);
        double y = p.x() * Math.sin(angle) + p.y() * Math.cos(angle);
        return new Point(x, y);
    }

    private int mapToCanvasX(Rect world, Point p, int canvasWidth) {
        return (int) ((p.x() - world.x()) / world.width() * canvasWidth);
    }

    private int mapToCanvasY(Rect world, Point p, int canvasHeight) {
        return (int) ((p.y() - world.y()) / world.height() * canvasHeight);
    }
}
