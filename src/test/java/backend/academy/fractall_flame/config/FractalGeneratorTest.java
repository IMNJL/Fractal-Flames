package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.DefaultFractalRenderer;
import backend.academy.fractall_flame.processing.FractalImage;
import backend.academy.fractall_flame.processing.MultiThreadFractalRenderer;
import backend.academy.fractall_flame.processing.SingleThreadFractalRenderer;
import backend.academy.fractall_flame.utils.ImageFormat;
import backend.academy.fractall_flame.utils.ImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FractalGeneratorTest {

    private Scanner mockScanner;
    private FractalGenerator generator;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        generator = new FractalGenerator(mockScanner);
    }

    @Test
    void testGenerateFractalWithDefaults() {
        // Подготовка моков
        when(mockScanner.nextLine())
            .thenReturn("y") // Использование параметров по умолчанию
            .thenReturn("2");
        when(mockScanner.next()).thenReturn("n"); // Не изменяем список трансформаций

        // Вызов метода
        generator.generateFractal();

        // Проверки
        verify(mockScanner, times(1)).nextLine(); // Проверяем, что nextLine() вызван
    }

    @Test
    void testGenerateFractalWithCustomParameters() {
        // Подготовка моков
        when(mockScanner.nextLine()).thenReturn("n"); // Пользовательский ввод
        when(mockScanner.nextInt()).thenReturn(1); // Выбор однопоточного режима
        when(mockScanner.next()).thenReturn(""); // Игнор дополнительных вызовов

        // Заглушки для ввода параметров
        when(mockScanner.nextLine())
            .thenReturn("800") // Ширина
            .thenReturn("600") // Высота
            .thenReturn("10")  // Количество выборок
            .thenReturn("100") // Итерации на выборку
            .thenReturn("2")   // Симметрия
            .thenReturn("12345"); // seed

        // Вызов метода
        generator.generateFractal();

        // Проверки
        verify(mockScanner, times(1)).next();
    }

    @Test
    void testChooseThreadModeSingleThread() {
        // Подготовка моков
        when(mockScanner.nextInt()).thenReturn(1);

        // Вызов метода
        DefaultFractalRenderer renderer = FractalGenerator.chooseThreadMode(12345L);

        // Проверка
        assertTrue(renderer instanceof SingleThreadFractalRenderer);
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testChooseThreadModeMultiThread() {
        // Подготовка моков
        when(mockScanner.nextInt()).thenReturn(2);

        // Вызов метода
        DefaultFractalRenderer renderer = FractalGenerator.chooseThreadMode(12345L);

        // Проверка
        assertTrue(renderer instanceof MultiThreadFractalRenderer);
        verify(mockScanner, times(1)).nextInt();
    }
}
