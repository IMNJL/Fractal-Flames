package backend.academy.fractall_flame.utils;

import backend.academy.fractall_flame.config.Pixel;
import backend.academy.fractall_flame.processing.FractalImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;
import static backend.academy.fractall_flame.config.Config.RGB;

@UtilityClass
public final class ImageUtils {
    private static final int NBIT = 8;
    private static final int CONST = 3;

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        int width = image.width();
        int height = image.height();

        // Создаем BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = image.pixel(x, y);

                // Распределение цветов от интенсивности
                int rgb = intensivityDistribution(pixel);

                // Установка цвета пикселя в BufferedImage
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        // формат файла:"png", "jpeg")
        String fileFormat = format.name().toLowerCase();

        try {
            // Сохраняем файл
            ImageIO.write(bufferedImage, fileFormat, filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении изображения: " + filename, e);
        }
    }

    private static int intensivityDistribution(Pixel pixel) {
        int red = Math.min(RGB, pixel.r());
        int green = Math.min(RGB, pixel.g());
        int blue = Math.min(RGB, pixel.b());

        // Усиление цвета на основе плотности
        int intensity = pixel.hitCount(); // Используем hitCount для подсветки
        red = Math.min(RGB, red + intensity * 2);
        green = Math.min(RGB, green + intensity);
        blue = Math.min(RGB, blue + intensity * CONST / 2);

        // Применение ограничений для баланса цветов
        int rgb = (red << 2 * NBIT) | (green << NBIT) | blue;
        return rgb;
    }
}
