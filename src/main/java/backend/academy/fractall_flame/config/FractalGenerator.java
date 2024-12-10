package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.DefaultFractalRenderer;
import backend.academy.fractall_flame.processing.FractalImage;
import backend.academy.fractall_flame.processing.MultiThreadFractalRenderer;
import backend.academy.fractall_flame.processing.SingleThreadFractalRenderer;
import backend.academy.fractall_flame.transformations.Transformation;
import backend.academy.fractall_flame.utils.ImageFormat;
import backend.academy.fractall_flame.utils.ImageUtils;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import lombok.extern.log4j.Log4j2;
import static backend.academy.fractall_flame.config.Config.MILLISEC;
import static backend.academy.fractall_flame.config.Config.OUTPUT_PATH;
import static backend.academy.fractall_flame.config.Config.getDoubleInput;
import static backend.academy.fractall_flame.config.Config.getIntInput;
import static backend.academy.fractall_flame.config.Config.getLongInput;
import static backend.academy.fractall_flame.config.TransformationConfig.configureTransformations;

@Log4j2
public class FractalGenerator {
    private static final LocalDateTime NOW = LocalDateTime.now();

    public void generateFractal() {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

        // Ввод пользовательских параметров
        log.info("Хотите использовать параметры по умолчанию? (y/n) >>> ");

        String useDefaults = sc.nextLine().trim().toLowerCase();
        // Используем параметры из конфигурации
        int width = Config.WIDTH;
        int height = Config.HEIGHT;
        int samples = Config.SAMPLES;
        short iterPerSample = Config.ITER_PER_SAMPLE;
        int symmetry = Config.SYMMETRY;
        long seed = Config.SEED;
        Rect world = Config.WORLD;

        if (!useDefaults.equals("y")) {
            log.info("Введите ширину изображения (по умолчанию {}): >>> ", Config.WIDTH);
            width = getIntInput(sc, Config.WIDTH);

            log.info("Введите высоту изображения (по умолчанию {}): >>> ", Config.HEIGHT);
            height = getIntInput(sc, Config.HEIGHT);

            log.info("Введите количество выборок (по умолчанию {}): >>> ", Config.SAMPLES);
            samples = getIntInput(sc, Config.SAMPLES);

            log.info("Введите итерации на выборку (по умолчанию {}): >>> ", Config.ITER_PER_SAMPLE);
            iterPerSample = (short) getIntInput(sc, Config.ITER_PER_SAMPLE);

            log.info("Введите количество симметрий (по умолчанию {}): >>> ", Config.SYMMETRY);
            symmetry = getIntInput(sc, Config.SYMMETRY);

            log.info("Введите значение seed (по умолчанию {}): >>> ", Config.SEED);
            seed = getLongInput(sc, Config.SEED);

            log.info("Введите параметры мира (по умолчанию {}): >>> ", Config.WORLD);
            log.info("Левая граница X: ");
            double x = getDoubleInput(sc, Config.WORLD.x());
            log.info("Верхняя граница Y: ");
            double y = getDoubleInput(sc, Config.WORLD.y());
            log.info("Ширина: ");
            double widthWorld = getDoubleInput(sc, Config.WORLD.width());
            log.info("Высота: ");
            double heightWorld = getDoubleInput(sc, Config.WORLD.height());
            world = new Rect(x, y, widthWorld, heightWorld);
        }

        log.debug("Starting fractal generation with parameters:"
            + " width={}, height={}, samples={}", width, height, samples);


        // выбор режима
        log.info("Выберете режим рендеринга: 1 - однопоточный, 2 - многопоточный>>> ");
        DefaultFractalRenderer renderer = chooseThreadMode(sc, seed);

        // Холст
        FractalImage canvas = FractalImage.create(width, height);

        // Добавление градиента
        HSBGradient gradient = new HSBGradient();
        canvas.colorGradient(gradient);

        List<Transformation> transformations = configureTransformations(sc);

        // начало замера
        long startTime = System.nanoTime();

        // Генерация фрактала
        log.info("Рендеринг начался...");

        renderer.render(canvas, world, transformations, samples, iterPerSample, symmetry, gradient);
        long endTime = System.nanoTime(); // конец замера

        log.info("Генерация завершена. Время рендеринга: {} мс", (endTime - startTime) / MILLISEC);

        // сохранение изображения в отдельном методе
       saveImage(canvas);
    }

    protected static DefaultFractalRenderer chooseThreadMode(Scanner sc, long seed) {
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

    private void saveImage(FractalImage canvas) {
        // создаем время генерации, чтобы не перезаписывать и не менять вручную
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = NOW.format(formatter);
        String outputPath = String.format(OUTPUT_PATH, formattedDateTime);

        ImageUtils.save(canvas, Path.of(outputPath), ImageFormat.PNG);
        log.info("Изображение сохранено в файл: {}", outputPath);
    }
}
