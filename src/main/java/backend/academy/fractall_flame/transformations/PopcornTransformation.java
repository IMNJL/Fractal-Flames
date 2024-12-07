package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class PopcornTransformation implements Transformation {
    private final double a;
    private final double b;
    private final Color color;
    private static final int CONST = 3;

    public PopcornTransformation(double a, double b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    @Override
    public Point apply(Point point) {
        double newX = point.x() + a * Math.sin(Math.tan(CONST * point.y()));
        double newY = point.y() + b * Math.sin(Math.tan(CONST * point.x()));
        return new Point(newX, newY);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }
}
