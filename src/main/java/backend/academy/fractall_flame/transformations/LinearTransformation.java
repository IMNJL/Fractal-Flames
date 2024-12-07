package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public class LinearTransformation implements Transformation {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public LinearTransformation(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public Point apply(Point p) {
        double x = a * p.x() + b * p.y();
        double y = c * p.x() + d * p.y();
        return new Point(x, y);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }
}
