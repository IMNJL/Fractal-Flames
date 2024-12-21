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
    
        generator = new FractalGenerator();
    }

    @Test
    void testChooseThreadMode() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(2); // Выбираем многопоточный режим

        FractalGenerator.scanner(mockScanner); // Подменяем Scanner
        FractalGenerator generator = new FractalGenerator();
        DefaultFractalRenderer renderer = generator.chooseThreadMode(123L);

        assertTrue(renderer instanceof MultiThreadFractalRenderer);
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testChooseThreadMode_DefaultOnInvalidInput() {
        // Мок для Scanner
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(99); // Некорректный ввод

        // Установка мока
        FractalGenerator.scanner(mockScanner);

        // Выполнение метода
        FractalGenerator generator = new FractalGenerator();
        DefaultFractalRenderer renderer = generator.chooseThreadMode(123L);

        // Проверка
        assertTrue(renderer instanceof SingleThreadFractalRenderer, "Ожидался SingleThreadFractalRenderer");
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testGenerateFractal_WithCustomParameters() {
        // Мок для Scanner
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
            .thenReturn("n") // Пользователь не хочет использовать параметры по умолчанию
            .thenReturn("800") // Ширина
            .thenReturn("600") // Высота
            .thenReturn("500") // Количество выборок
            .thenReturn("50") // Итерации на выборку
            .thenReturn("4") // Симметрии
            .thenReturn("123456789") // Seed
            .thenReturn("0.0") // Левая граница X
            .thenReturn("0.0") // Верхняя граница Y
            .thenReturn("2.0") // Ширина мира
            .thenReturn("2.0"); // Высота мира

        FractalGenerator.scanner(mockScanner);

        // Выполнение метода
        FractalGenerator generator = new FractalGenerator();
        generator.generateFractal();

        // Проверка, что Scanner вызывался нужное количество раз
        verify(mockScanner, times(1)).next();
    }
}
