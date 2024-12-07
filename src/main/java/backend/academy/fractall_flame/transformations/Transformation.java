package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import java.awt.Color;

public interface Transformation {
    Point apply(Point point);  // Применяет трансформацию

    Color getColor(); // Возвращает цвет для этой трансформации
}
