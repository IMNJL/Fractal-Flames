package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class SinusoidalTransformation implements Transformation {
    private final Color color;

    public SinusoidalTransformation(Color color) {
        this.color = color;
    }

    @Override
    public Point apply(Point point) {
        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());
        return new Point(newX, newY);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }

}
