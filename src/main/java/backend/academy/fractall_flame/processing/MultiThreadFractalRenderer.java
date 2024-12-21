package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("PREDICTABLE_RANDOM")
public class MultiThreadFractalRenderer extends SingleThreadFractalRenderer {

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
        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {

            int samplesPerThread = samples / numThreads;
            Future<?>[] tasks = new Future<?>[numThreads];

            for (int i = 0; i < numThreads; i++) {
                final int start = i * samplesPerThread;
                final int end = (i == numThreads - 1) ? samples : start + samplesPerThread;

                tasks[i] = executor.submit(() -> {
                    processing(canvas, world, variations, iterPerSample, symmetry, colorGradient, start, end);
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
        }

        BrightnessNormalizer.normalize(canvas);
        return canvas;
    }
}
