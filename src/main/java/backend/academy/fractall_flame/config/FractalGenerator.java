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
import lombok.Getter;
import lombok.Setter;
import static backend.academy.fractall_flame.config.Config.HEIGHT;
import static backend.academy.fractall_flame.config.Config.ITER_PER_SAMPLE;
import static backend.academy.fractall_flame.config.Config.MILLISEC;
import static backend.academy.fractall_flame.config.Config.OUTPUT_PATH;
import static backend.academy.fractall_flame.config.Config.SAMPLES;
import static backend.academy.fractall_flame.config.Config.SEED;
import static backend.academy.fractall_flame.config.Config.SYMMETRY;
import static backend.academy.fractall_flame.config.Config.WIDTH;
import static backend.academy.fractall_flame.config.Config.WORLD;
import static backend.academy.fractall_flame.config.Config.getDoubleInput;
import static backend.academy.fractall_flame.config.Config.getIntInput;
import static backend.academy.fractall_flame.config.Config.getLongInput;


@Log4j2
@SuppressWarnings({"FCCD_FIND_CLASS_CIRCULAR_DEPENDENCY", "LSC_LITERAL_STRING_COMPARISON", "CLI_CONSTANT_LIST_INDEX"})
public class FractalGenerator {
    @Getter @Setter private static Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private static final String INPUT_LINE = " >>> ";
    private static final String YES = "y";

    public void generateFractal() {
        // Ввод пользовательских параметров
        log.info("Хотите использовать параметры по умолчанию? (y/n) >>> ");
        String useDefaults = scanner.nextLine().trim().toLowerCase();

        // Настройка параметров
        int width = WIDTH;
        int height = HEIGHT;
        int samples = SAMPLES;
        short iterPerSample = ITER_PER_SAMPLE;
        int symmetry = SYMMETRY;
        long seed = SEED;
        Rect world = WORLD;

        if (!YES.equals(useDefaults)) {
            width = promptForInt("Введите ширину изображения (по умолчанию " + WIDTH + INPUT_LINE, WIDTH);
            height = promptForInt("Введите высоту изображения (по умолчанию " + HEIGHT + INPUT_LINE, HEIGHT);
            samples = promptForInt("Введите количество выборок (по умолчанию " + SAMPLES + INPUT_LINE, SAMPLES);
            iterPerSample = (short) promptForInt("Введите итерации на выборку (по умолчанию " + ITER_PER_SAMPLE
            + INPUT_LINE, ITER_PER_SAMPLE);
            symmetry = promptForInt("Введите количество симметрий (по умолчанию " + SYMMETRY + INPUT_LINE, SYMMETRY);
            seed = promptForLong("Введите значение seed (по умолчанию " + SEED + INPUT_LINE, SEED);

            log.info("Введите параметры мира (по умолчанию {}): >>> ", WORLD);
            double x = promptForDouble("Левая граница X: ", WORLD.x());
            double y = promptForDouble("Верхняя граница Y: ", WORLD.y());
            double widthWorld = promptForDouble("Ширина: ", WORLD.width());
            double heightWorld = promptForDouble("Высота: ", WORLD.height());
            world = new Rect(x, y, widthWorld, heightWorld);
        }

        log.debug("Starting fractal generation with parameters: width={}, height={}, samples={}", width,
        height, samples);

        // Выбор режима
        DefaultFractalRenderer renderer = chooseThreadMode(seed);

        // Холст
        FractalImage canvas = FractalImage.create(width, height);

        // Градиент
        HSBGradient gradient = new HSBGradient();
        canvas.colorGradient(gradient);

        List<Transformation> transformations = TransformationConfig.configureTransformations(scanner);

        // Рендеринг
        long startTime = System.nanoTime();
        log.info("Рендеринг начался...");
        renderer.render(canvas, world, transformations, samples, iterPerSample, symmetry, gradient);
        long endTime = System.nanoTime();
        log.info("Генерация завершена. Время рендеринга: {} мс", (endTime - startTime) / MILLISEC);

        // Сохранение изображения
        saveImage(canvas);
    }

    private int promptForInt(String message, int defaultValue) {
        log.info(message);
        return getIntInput(scanner, defaultValue);
    }

    private long promptForLong(String message, long defaultValue) {
        log.info(message);
        return getLongInput(scanner, defaultValue);
    }

    private double promptForDouble(String message, double defaultValue) {
        log.info(message);
        return getDoubleInput(scanner, defaultValue);
    }

    protected static DefaultFractalRenderer chooseThreadMode(long seed) {
        log.info("Выберете режим рендеринга: 1 - однопоточный, 2 - многопоточный >>> ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                log.info("Выбран однопоточный режим.");
                return new SingleThreadFractalRenderer(seed);
            case 2:
                log.info("Выбран многопоточный режим.");
                return new MultiThreadFractalRenderer(seed);
            default:
                log.info("Некорректный ввод, выбран однопоточный режим по умолчанию.");
                return new SingleThreadFractalRenderer(seed);
        }
    }

    protected void saveImage(FractalImage canvas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        String outputPath = String.format(OUTPUT_PATH, formattedDateTime);

        ImageUtils.save(canvas, Path.of(outputPath), ImageFormat.PNG);
        log.info("Изображение сохранено в файл: {}", outputPath);
    }
}
