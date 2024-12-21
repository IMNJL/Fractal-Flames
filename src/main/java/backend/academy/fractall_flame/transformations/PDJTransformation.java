package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;
import lombok.Getter;

public class PDJTransformation implements Transformation {
    @Getter private final double p1;
    @Getter private final double p2;
    @Getter private final double p3;
    @Getter private final double p4;

    public PDJTransformation(double p1, double p2, double p3, double p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX = Math.sin(p1 * y) - Math.cos(p2 * x);
        double newY = Math.sin(p3 * x) - Math.cos(p4 * y);
        return new Point(newX, newY);
    }

    @Override
    public Color getColor() {
        // Устанавливаем цвет для этой трансформации
        return Color.BLUE;
    }
}
