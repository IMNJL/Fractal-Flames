package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;

@SuppressWarnings({"PREDICTABLE_RANDOM", "ParameterNumber"})
public abstract class DefaultFractalRenderer {
    protected final SecureRandom secureRandom = new SecureRandom(); // Singleton

    public DefaultFractalRenderer(long seed) {
        secureRandom.setSeed(seed);
    }

    public abstract FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        ColorGradient colorGradient
    );

    public Point randomPoint(Rect world, SecureRandom random) {
        if (world == null) {
            throw new IllegalArgumentException("World rectangle cannot be null");
        }
        double x = world.x() + secureRandom.nextDouble() * world.width();
        double y = world.y() + secureRandom.nextDouble() * world.height();
        return new Point(x, y);
    }

    public Point rotate(Point p, double angle) {
        double x = p.x() * Math.cos(angle) - p.y() * Math.sin(angle);
        double y = p.x() * Math.sin(angle) + p.y() * Math.cos(angle);
        return new Point(x, y);
    }

    public int mapToCanvasX(Rect world, Point p, int canvasWidth) {
        return (int) ((p.x() - world.x()) / world.width() * canvasWidth);
    }

    public int mapToCanvasY(Rect world, Point p, int canvasHeight) {
        return (int) ((p.y() - world.y()) / world.height() * canvasHeight);
    }

    public void applySymmetryAndColor(
        FractalImage canvas, Rect world, Point point, int symmetry, ColorGradient colorGradient
    ) {
        // Симметрия и цвет
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
                canvas.setPixel(x, y, new Pixel(
                    colorGradient.apply(rotated.x(), rotated.y(), iterations),
                    iterations
                ));
            }
        }
    }

    public void processing(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int iterPerSample,
        int symmetry,
        ColorGradient colorGradient,
        int start,
        int end
    ) {

        for (int num = start; num < end; ++num) {
            Point point = randomPoint(world, secureRandom);
            double color = secureRandom.nextDouble();

            for (int step = 0; step < iterPerSample; ++step) {
                Transformation transformation = variations.get(secureRandom.nextInt(variations.size()));
                point = transformation.apply(point);
                color = (color + secureRandom.nextDouble()) / 2.0;

                applySymmetryAndColor(canvas, world, point, symmetry, colorGradient);
            }
        }
    }
}
