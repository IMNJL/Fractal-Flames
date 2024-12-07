package backend.academy.fractall_flame;

import backend.academy.fractall_flame.config.FractalGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void testMainMethod() {
        // Мокаем создание экземпляров FractalGenerator
        try (MockedConstruction<FractalGenerator> mockedConstruction = mockConstruction(FractalGenerator.class)) {
            // Запускаем метод main
            Main.main(new String[]{});

            // Проверяем, что метод generateFractal() был вызван
            FractalGenerator mockGenerator = mockedConstruction.constructed().get(0); // Получаем первый созданный экземпляр
            verify(mockGenerator, times(1)).generateFractal();
        }
    }
}
