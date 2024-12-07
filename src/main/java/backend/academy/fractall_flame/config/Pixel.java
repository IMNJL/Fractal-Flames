package backend.academy.fractall_flame.config;

import java.awt.Color;

public record Pixel(int r, int g, int b, int hitCount) {
    public Pixel(Color color, int hitCount) {
        this(color.getRed(), color.getGreen(), color.getBlue(), hitCount);
    }
}
