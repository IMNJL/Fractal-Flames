package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class HeartTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        // Пример простой трансформации (для иллюстрации)
        double x = point.x() * point.x() - point.y() * point.y();
        double y = 2 * point.x() * point.y();
        return new Point(x, y);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.RED;
    }
}
