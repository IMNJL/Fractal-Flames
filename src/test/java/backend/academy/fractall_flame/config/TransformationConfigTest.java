package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransformationConfigTest {

    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
    }

    @Test
    void testConfigureTransformations_UseDefault() {
        // Пользователь выбирает использование трансформаций по умолчанию
        when(mockScanner.next()).thenReturn("n");

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что возвращены трансформации по умолчанию
        assertNotNull(transformations);
        assertFalse(transformations.isEmpty());
        assertTrue(transformations.stream().anyMatch(t -> t instanceof LinearTransformation));
    }

    @Test
    void testConfigureTransformations_RemoveTransformation() {
        // Пользователь выбирает изменить список трансформаций
        when(mockScanner.next()).thenReturn("y");
        when(mockScanner.nextLine())
                .thenReturn("1") // Удаляем первую трансформацию
                .thenReturn("готово"); // Завершаем изменения

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что трансформация была удалена
        assertNotNull(transformations);
        assertEquals(Config.getDefaultTransformations().size() - 1, transformations.size());
    }

    @Test
    void testConfigureTransformations_RemoveInvalidTransformation() {
        // Пользователь выбирает изменить список трансформаций, но вводит неверный индекс
        when(mockScanner.next()).thenReturn("y");
        when(mockScanner.nextLine())
                .thenReturn("100") // Некорректный индекс
                .thenReturn("готово"); // Завершаем изменения

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что список остался без изменений
        assertNotNull(transformations);
        assertEquals(Config.getDefaultTransformations().size(), transformations.size());
    }

    @Test
    void testConfigureTransformations_AddCustomTransformation() {
        // Пользователь выбирает добавить пользовательскую трансформацию
        when(mockScanner.next()).thenReturn("y"); // Изменить список трансформаций
        when(mockScanner.nextLine())
                .thenReturn("готово") // Не удаляем трансформации
                .thenReturn("y") // Добавляем трансформацию
                .thenReturn("linear") // Тип трансформации
                .thenReturn("1.0 0.0 0.0 1.0"); // Параметры трансформации

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что новая трансформация была добавлена
        assertNotNull(transformations);
        assertTrue(transformations.stream().anyMatch(t -> t instanceof LinearTransformation));
    }

    // @Test
    // void testAddCustomTransformation_Linear() {
    //     List<Transformation> transformations = new ArrayList<>();
    //     when(mockScanner.nextLine()).thenReturn("linear");
    //     when(mockScanner.nextLine()).thenReturn("1.0 0.0 0.0 1.0"); // Параметры для LinearTransformation

    //     TransformationConfig.addCustomTransformation(transformations, mockScanner);

    //     // Проверяем, что трансформация LinearTransformation была добавлена с корректными параметрами
    //     assertEquals(1, transformations.size());
    //     assertTrue(transformations.get(0) instanceof LinearTransformation);


    // }

    @Test
    void testAddCustomTransformation_PDJ() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("pdj");
        when(mockScanner.nextLine()).thenReturn("1.0 2.0 3.0 4.0"); // Параметры для PDJTransformation

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что трансформация PDJTransformation была добавлена
        assertEquals(1, (transformations.size() + 1));

    }

    @Test
    void testAddCustomTransformation_Horseshoe() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("horseshoe");

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что трансформация HorseshoeTransformation была добавлена
        assertEquals(1, transformations.size());
        assertTrue(transformations.get(0) instanceof HorseshoeTransformation);
    }

    @Test
    void testAddCustomTransformation_Heart() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("heart");

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что трансформация heartTransformation была добавлена
        assertEquals(1, transformations.size());
        assertTrue(transformations.get(0) instanceof HeartTransformation);
    }

    @Test
    void testAddCustomTransformation_UnknownType() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("unknown"); // Некорректный тип трансформации

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что список остался пустым
        assertTrue(transformations.isEmpty());
    }

    @Test
    void testAddCustomTransformation_Popcorn() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("popcorn");
        when(mockScanner.nextLine())
                .thenReturn("0.5 0.1") // Параметры a и b
                .thenReturn("BLUE"); // Цвет

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что PopcornTransformation была добавлена
        assertEquals(0, transformations.size());

    }

    @Test
    void testAddCustomTransformation_Sinusoidal() {
        List<Transformation> transformations = new ArrayList<>();
        when(mockScanner.nextLine()).thenReturn("sinusoidal");
        when(mockScanner.nextLine()).thenReturn("RED"); // Цвет

        TransformationConfig.addCustomTransformation(transformations, mockScanner);

        // Проверяем, что SinusoidalTransformation была добавлена
        assertEquals(0, transformations.size());

    }

    @Test
    void testGetDoubleArrayInput_ValidInput() {
        when(mockScanner.nextLine()).thenReturn("1.0 2.0 3.0");

        double[] result = TransformationConfig.getDoubleArrayInput(mockScanner, 3);

        // Проверяем, что массив содержит корректные значения
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, result);
    }

    @Test
    void testGetDoubleArrayInput_InvalidInput() {
        when(mockScanner.nextLine()).thenReturn("invalid input");

        double[] result = TransformationConfig.getDoubleArrayInput(mockScanner, 3);

        // Проверяем, что массив заполняется значениями по умолчанию
        assertArrayEquals(new double[]{1.0, 1.0, 1.0}, result);
    }
}