package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.FractalImage;
import backend.academy.fractall_flame.processing.MultiThreadFractalRenderer;
import backend.academy.fractall_flame.processing.Renderer;
import backend.academy.fractall_flame.processing.SingleThreadFractalRenderer;
import backend.academy.fractall_flame.utils.ImageFormat;
import backend.academy.fractall_flame.utils.ImageUtils;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import lombok.extern.log4j.Log4j2;
import static backend.academy.fractall_flame.config.Config.MILLISEC;
import static backend.academy.fractall_flame.config.Config.OUTPUT_PATH;

@Log4j2
public class FractalGenerator {

    public void generateFractal() {
        // Используем параметры из конфигурации
        int width = Config.WIDTH;
        int height = Config.HEIGHT;
        int samples = Config.SAMPLES;
        short iterPerSample = Config.ITER_PER_SAMPLE;
        int symmetry = Config.SYMMETRY;
        long seed = Config.SEED;
        Rect world = Config.WORLD;

        log.debug("Starting fractal generation with parameters:"
            + " width={}, height={}, samples={}", width, height, samples);

        // выбор режима
        log.info("Выберете режим рендеринга: 1 - однопоточный, 2 - многопоточный>>> ");
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        Renderer renderer = chooseThreadMode(sc, seed);

        // Холст
        FractalImage canvas = FractalImage.create(width, height);

        // Добавление градиента
        HSBGradient gradient = new HSBGradient();
        canvas.colorGradient(gradient);

        // начало замера
        long startTime = System.nanoTime();

        // Генерация фрактала
        log.info("Рендеринг начался...");
        renderer.render(canvas, world, Config.TRANSFORMATIONS, samples, iterPerSample, symmetry, gradient);
        long endTime = System.nanoTime(); // конец замера

        log.info("Генерация завершена. Время рендеринга: {} мс", (endTime - startTime) / MILLISEC);

        // Сохранение изображения
        ImageUtils.save(canvas, Path.of(OUTPUT_PATH), ImageFormat.PNG);
        log.info("Изображение сохранено в файл: {}", OUTPUT_PATH);
    }

    private static Renderer chooseThreadMode(Scanner sc, long seed) {
        int choice = sc.nextInt();
        return switch (choice) {
            case 1 -> {
                log.info("Выбран однопоточный режим.");
                yield new SingleThreadFractalRenderer(seed);
            }
            case 2 -> {
                log.info("Выбран многопоточный режим.");
                yield new MultiThreadFractalRenderer(seed);
            }
            default -> {
                log.info("Некорректный ввод, выбран однопоточный режим по умолчанию.");
                yield new SingleThreadFractalRenderer(seed);
            }
        };
    }
}
