package backend.academy.fractall_flame.transformations;

import backend.academy.fractall_flame.config.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class SwirlTransformationTest {

    @Test
    void testApplyTransformation() {
        // Создаем экземпляр трансформации с некоторым коэффициентом
        SwirlTransformation transformation = new SwirlTransformation(0.5);

        // Тестовая точка
        Point point = new Point(1.0, 0.0); // точка на оси X

        // Применяем трансформацию
        Point transformedPoint = transformation.apply(point);

        // Ожидаемые значения для r и theta
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        theta += 0.5 * r;  // применяем трансформацию

        // Проверяем, что трансформированные координаты верны
        double expectedX = r * Math.cos(theta);
        double expectedY = r * Math.sin(theta);

        assertEquals(expectedX, transformedPoint.x(), 1e-6); // допускаем небольшую погрешность
        assertEquals(expectedY, transformedPoint.y(), 1e-6);
    }

    @Test
    void testGetColor() {
        // Создаем экземпляр трансформации
        SwirlTransformation transformation = new SwirlTransformation(0.5);

        // Проверяем, что цвет трансформации правильный
        assertEquals(Color.BLUE, transformation.getColor());
    }
}
