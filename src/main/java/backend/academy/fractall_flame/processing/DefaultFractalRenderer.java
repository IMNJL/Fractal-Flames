package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;

@SuppressWarnings("PREDICTABLE_RANDOM")
public class DefaultFractalRenderer implements Renderer {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); // Singleton

    public DefaultFractalRenderer(long seed) {
        SECURE_RANDOM.setSeed(seed);
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
        return null;
    }

    Point randomPoint(Rect world) {
        double x = world.x() + SECURE_RANDOM.nextDouble() * world.width();
        double y = world.y() + SECURE_RANDOM.nextDouble() * world.height();
        return new Point(x, y);
    }

    Point rotate(Point p, double angle) {
        double x = p.x() * Math.cos(angle) - p.y() * Math.sin(angle);
        double y = p.x() * Math.sin(angle) + p.y() * Math.cos(angle);
        return new Point(x, y);
    }

    int mapToCanvasX(Rect world, Point p, int canvasWidth) {
        return (int) ((p.x() - world.x()) / world.width() * canvasWidth);
    }

    int mapToCanvasY(Rect world, Point p, int canvasHeight) {
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
}
