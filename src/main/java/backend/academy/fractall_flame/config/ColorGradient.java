package backend.academy.fractall_flame.config;

import java.awt.Color;

public interface ColorGradient {
    Color apply(double x, double y, int iterations);
}
