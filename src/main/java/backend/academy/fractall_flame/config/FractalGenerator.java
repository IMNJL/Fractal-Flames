package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.FractalImage;
import backend.academy.fractall_flame.processing.FractalRenderer;
import backend.academy.fractall_flame.utils.ImageFormat;
import backend.academy.fractall_flame.utils.ImageUtils;
import java.nio.file.Path;
import lombok.extern.log4j.Log4j2;

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

        // Холст
        FractalImage canvas = FractalImage.create(width, height);

        // Добавление градиента
        HSBGradient gradient = new HSBGradient();
        canvas.colorGradient(gradient);

        FractalRenderer fractalRenderer = new FractalRenderer(seed);
        // Генерация фрактала
        fractalRenderer.render(canvas, world, Config.TRANSFORMATIONS, samples, iterPerSample, symmetry, gradient);
        log.info("Генерация завершена.");

        // Сохранение изображения
        String outputPath = "src/main/resources/fractal_heart_11.png";
        ImageUtils.save(canvas, Path.of(outputPath), ImageFormat.PNG);
        log.info(STR."Изображение сохранено в файл: \{outputPath}");
    }
}
