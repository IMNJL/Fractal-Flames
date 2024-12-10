package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.processing.DefaultFractalRenderer;
import backend.academy.fractall_flame.processing.MultiThreadFractalRenderer;
import backend.academy.fractall_flame.processing.SingleThreadFractalRenderer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FractalGeneratorTest {

    @Test
    void testChooseSingleThreadMode() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1); // Ввод "1" для однопоточного режима

        DefaultFractalRenderer renderer = FractalGenerator.chooseThreadMode(mockScanner, 12345L);

        assertNotNull(renderer);
        assertTrue(renderer instanceof SingleThreadFractalRenderer);
    }

    @Test
    void testChooseMultiThreadMode() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(2); // Ввод "2" для многопоточного режима

        DefaultFractalRenderer renderer = FractalGenerator.chooseThreadMode(mockScanner, 12345L);

        assertNotNull(renderer);
        assertTrue(renderer instanceof MultiThreadFractalRenderer);
    }

    @Test
    void testChooseInvalidMode() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(0); // Некорректный ввод

        DefaultFractalRenderer renderer = FractalGenerator.chooseThreadMode(mockScanner, 12345L);

        assertNotNull(renderer);
        assertTrue(renderer instanceof SingleThreadFractalRenderer); // Ожидаем однопоточный режим по умолчанию
    }
}
