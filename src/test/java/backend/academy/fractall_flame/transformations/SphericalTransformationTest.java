package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class SphericalTransformationTest {

    @Test
    void testApplyTransformation() {
        // Создаем экземпляр трансформации с коэффициентом масштаба
        double scale = 2.0;
        SphericalTransformation transformation = new SphericalTransformation(scale);

        // Тестовая точка
        Point point = new Point(1.0, 1.0); // точка на прямой y = x

        // Применяем трансформацию
        Point transformedPoint = transformation.apply(point);

        // Вычисляем радиус квадрата
        double rSquared = point.x() * point.x() + point.y() * point.y();
        double factor = scale / (rSquared + SphericalTransformation.EPSILON); // расчет масштаба

        // Проверяем, что трансформированные координаты верны
        double expectedX = point.x() * factor;
        double expectedY = point.y() * factor;

        assertEquals(expectedX, transformedPoint.x(), 1e-6); // допускаем небольшую погрешность
        assertEquals(expectedY, transformedPoint.y(), 1e-6);
    }

    @Test
    void testGetColor() {
        // Создаем экземпляр трансформации
        double scale = 2.0;
        SphericalTransformation transformation = new SphericalTransformation(scale);

        // Проверяем, что цвет трансформации правильный
        assertEquals(Color.ORANGE, transformation.getColor());
    }
}
