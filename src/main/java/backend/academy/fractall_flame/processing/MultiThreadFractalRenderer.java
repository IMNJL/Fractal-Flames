package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Point;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("PREDICTABLE_RANDOM")
public class MultiThreadFractalRenderer extends DefaultFractalRenderer implements Renderer {

    public MultiThreadFractalRenderer(long seed) {
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
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int samplesPerThread = samples / numThreads;
        Future<?>[] tasks = new Future<?>[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * samplesPerThread;
            final int end = (i == numThreads - 1) ? samples : start + samplesPerThread;
            final SecureRandom threadRandom = new SecureRandom(); // Локальный Random для каждого потока

            tasks[i] = executor.submit(() -> {
                for (int num = start; num < end; ++num) {
                    Point point = randomPoint(world, threadRandom);
                    double color = threadRandom.nextDouble();

                    for (int step = 0; step < iterPerSample; ++step) {
                        Transformation transformation = variations.get(threadRandom.nextInt(variations.size()));
                        point = transformation.apply(point);
                        color = (color + threadRandom.nextDouble()) / 2.0;

                        applySymmetryAndColor(canvas, world, point, symmetry, colorGradient);
                    }
                }
            });
        }

        // Ожидаем завершения всех потоков
        for (Future<?> task : tasks) {
            try {
                task.get();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка в многопоточном рендеринге", e);
            }
        }

        executor.shutdown();

        BrightnessNormalizer.normalize(canvas);
        return canvas;
    }

    private Point randomPoint(Rect world, SecureRandom random) {
        double x = world.x() + random.nextDouble() * world.width();
        double y = world.y() + random.nextDouble() * world.height();
        return new Point(x, y);
    }
}
