package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Pixel;
import java.awt.Color;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FractalImage {
    private final Pixel[] data;
    private final int width;
    private final int height;
    private ColorGradient colorGradient;

    public FractalImage(Pixel[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(0, 0, 0, 0); // Инициализация черным цветом
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (!contains(x, y)) {
            exceptionMethod();
        }
        return data[y * width + x];
    }

    private static void exceptionMethod() {
        throw new IllegalArgumentException("Pixel out of bounds");
    }

    public void setPixel(int x, int y, Pixel pixel) {
        if (!contains(x, y)) {
            exceptionMethod();
        }
        data[y * width + x] = pixel;
    }

    // Добавление градиента (смешивание)
    public void addColorGradient(ColorGradient gradient) {
        this.colorGradient = gradient;
    }

    public Color getColorAt(int x, int y, int iterations) {
        if (!contains(x, y)) {
            exceptionMethod();
        }

        Pixel pixel = pixel(x, y);

        // Получаем цвет из градиента
        if (colorGradient != null) {
            return colorGradient.apply(x, y, iterations);
        }

        // Если градиент не задан, возвращаем цвет пикселя
        return new Color(pixel.r(), pixel.g(), pixel.b());
    }

}
