package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.config.TransformationConfig;
import backend.academy.fractall_flame.transformations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    void testDefaultTransformations() {
        when(mockScanner.next()).thenReturn("n"); // Пользователь выбирает "нет" для изменения трансформаций

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что возвращается список трансформаций по умолчанию
        assertNotNull(transformations);
        assertFalse(transformations.isEmpty());
        assertTrue(transformations.stream().anyMatch(t -> t instanceof LinearTransformation));
    }

    @Test
    void testRemoveTransformation() {
        when(mockScanner.next()).thenReturn("y"); // Пользователь выбирает "да" для изменения трансформаций
        when(mockScanner.nextLine())
            .thenReturn("1") // Удаляем первую трансформацию
            .thenReturn("готово"); // Завершаем удаление

        List<Transformation> transformations = TransformationConfig.configureTransformations(mockScanner);

        // Проверяем, что первая трансформация была удалена
        assertNotNull(transformations);
        assertEquals(Config.getDefaultTransformations().size() - 1, transformations.size());
    }
}
