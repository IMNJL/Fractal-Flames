package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class SphericalTransformation implements Transformation {
    static final double EPSILON = 1e-6;
    private final double scale;

    public SphericalTransformation(double scale) {
        this.scale = scale;
    }

    @Override
    public Point apply(Point point) {
        double rSquared = point.x() * point.x() + point.y() * point.y();
        double factor = scale / (rSquared + EPSILON); // добавляем небольшое значение, чтобы избежать деления на 0
        double newX = point.x() * factor;
        double newY = point.y() * factor;
        return new Point(newX, newY);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.ORANGE;
    }
}
