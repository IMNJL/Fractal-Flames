package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCustomTransformationTest {

    @Test
    void testAddLinearTransformation() {
        Scanner mockScanner = mock(Scanner.class);
        List<Transformation> transformations = new ArrayList<>();

        when(mockScanner.nextLine())
            .thenReturn("linear") // Тип трансформации
            .thenReturn("1.0 0.0 0.0 1.0"); // Параметры для `LinearTransformation`

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        assertEquals(1, transformations.size());
        assertTrue(transformations.get(0) instanceof LinearTransformation);
    }

    @Test
    void testAddPopcornTransformation() {
        Scanner mockScanner = mock(Scanner.class);
        List<Transformation> transformations = new ArrayList<>();

        when(mockScanner.nextLine())
            .thenReturn("popcorn") // Тип трансформации
            .thenReturn("0.5 0.1") // Параметры a и b
            .thenReturn("BLUE"); // Цвет

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        assertEquals(1, transformations.size());
        assertTrue(transformations.get(0) instanceof PopcornTransformation);

        PopcornTransformation popcorn = (PopcornTransformation) transformations.get(0);
        assertEquals(Color.BLUE, popcorn.getColor());
    }

    @Test
    void testAddInvalidTransformation() {
        Scanner mockScanner = mock(Scanner.class);
        List<Transformation> transformations = new ArrayList<>();

        when(mockScanner.nextLine()).thenReturn("invalid"); // Некорректный тип трансформации

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        assertTrue(transformations.isEmpty()); // Список должен остаться пустым
    }
}
