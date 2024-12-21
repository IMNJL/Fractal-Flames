package backend.academy.fractall_flame.config;

import java.awt.Color;
import static backend.academy.fractall_flame.config.Config.HUNDRED;
import static backend.academy.fractall_flame.config.Config.RGB;

public class DefaultColorGradient implements ColorGradient {
    private final int red;
    private final int green;
    private final int blue;

    public DefaultColorGradient() {
        this(RGB, RGB, RGB); // Белый цвет по умолчанию
    }

    public DefaultColorGradient(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Color apply(double x, double y, int iterations) {
        int brightness = (int) ((double) RGB * Math.min(1.0, iterations / HUNDRED));
        return new Color(Math.min(red, brightness), Math.min(green, brightness), Math.min(blue, brightness));
    }
}
