package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double newX = (p.x() - p.y()) * (p.x() + p.y());
        double newY = 2 * p.x() * p.y();
        return new Point(newX, newY);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }
}
