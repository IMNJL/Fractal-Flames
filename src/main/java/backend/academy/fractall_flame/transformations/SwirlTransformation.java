package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class SwirlTransformation implements Transformation {

    private final double factor;

    public SwirlTransformation(double factor) {
        this.factor = factor;
    }

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        theta += factor * r;
        return new Point(r * Math.cos(theta), r * Math.sin(theta));
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }
}
