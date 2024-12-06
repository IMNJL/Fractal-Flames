package backend.academy.fractall_flame.config;

import java.awt.Color;
import static backend.academy.fractall_flame.config.Config.RGB;

public class DefaultColorGradient implements ColorGradient {
    @Override
    public Color apply(double x, double y, int iterations) {

        return new Color(RGB, RGB, RGB); // Белый цвет по умолчанию
    }
}
