package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.HeartTransformation;
import backend.academy.fractall_flame.transformations.HorseshoeTransformation;
import backend.academy.fractall_flame.transformations.LinearTransformation;
import backend.academy.fractall_flame.transformations.PDJTransformation;
import backend.academy.fractall_flame.transformations.PopcornTransformation;
import backend.academy.fractall_flame.transformations.SinusoidalTransformation;
import backend.academy.fractall_flame.transformations.SphericalTransformation;
import backend.academy.fractall_flame.transformations.SwirlTransformation;
import backend.academy.fractall_flame.transformations.Transformation;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import lombok.extern.log4j.Log4j2;
import static backend.academy.fractall_flame.config.Config.LINEAR_COEF;
import static backend.academy.fractall_flame.config.Config.PDJ_COEF;
import static backend.academy.fractall_flame.config.Config.POPCORN_COEF;
import static backend.academy.fractall_flame.config.Config.SPHERICAL_COEFCOEF;
import static backend.academy.fractall_flame.config.Config.SWIRL_COEF;
import static backend.academy.fractall_flame.config.Config.THIRD_PARAM;
import static backend.academy.fractall_flame.config.Config.getColorInput;
import static backend.academy.fractall_flame.config.Config.getDoubleInput;

@Log4j2
public class TransformationConfig extends FractalGenerator {
    private static final int ONE = 1;
    private static final String YESS = "y";
    private static final String READY = "готово";

    public static List<Transformation> configureTransformations(Scanner sc) {
        List<Transformation> transformations = Config.getDefaultTransformations();
        log.info("Список доступных трансформаций по умолчанию: ");
        for (int i = 0; i < transformations.size(); i++) {
            log.info("{}: {}", i + 1, transformations.get(i).getClass().getSimpleName());
        }

        log.info("Хотите изменить список трансформаций? (y/n) >>> ");
        String response = sc.next();

        if (!YESS.equals(response)) {
            return transformations; // Используем трансформации по умолчанию
        }

        while (true) {
            log.info("Введите номер трансформации, которую хотите удалить, или 'готово' для завершения: ");
            String input = sc.nextLine().trim().toLowerCase();
            if (READY.equals(input)) {
                break;
            }
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < transformations.size()) {
                    log.info("Удалена трансформация: {}", transformations.get(index).getClass().getSimpleName());
                    transformations.remove(index);
                } else {
                    log.warn("Некорректный номер. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                log.warn("Некорректный ввод. Попробуйте снова.");
            }
        }

        log.info("Хотите добавить пользовательскую трансформацию? (y/n) >>> ");
        if (YESS.equals(sc.nextLine().trim().toLowerCase())) {
            addCustomTransformation(transformations, sc);
        }

        log.info("Итоговый список трансформаций: ");
        transformations.forEach(t -> log.info(t.getClass().getSimpleName()));
        return transformations;
    }

    @SuppressWarnings("CLI_CONSTANT_LIST_INDEX")
    static void addCustomTransformation(List<Transformation> transformations, Scanner sc) {
        log.info("Введите тип трансформации (Linear, PDJ, Swirl, Spherical, Heart): >>> ");
        String type = sc.nextLine().trim().toLowerCase();

        switch (type) {
            case "linear" -> {
                log.info("Введите параметры a, b, c, d через пробел (например: 1.0 0.0 0.0 1.0): >>> ");
                double[] params = getDoubleArrayInput(sc, LINEAR_COEF);
                transformations.add(new LinearTransformation(params[ONE - 1], params[ONE], params[ONE + 1],
                params[THIRD_PARAM]));
            }
            case "pdj" -> {
                log.info("Введите параметры p1, p2, p3, p4 через пробел: >>> ");
                double[] params = getDoubleArrayInput(sc, PDJ_COEF);
                transformations.add(new PDJTransformation(params[ONE - 1], params[ONE], params[ONE + 1],
                params[THIRD_PARAM]));
            }
            case "swirl" -> {
                log.info("Введите параметр factor (например: 0.8): >>> ");
                double factor = getDoubleInput(sc, SWIRL_COEF);
                transformations.add(new SwirlTransformation(factor));
            }
            case "spherical" -> {
                log.info("Введите параметр scale (например: 1.6): >>> ");
                double scale = getDoubleInput(sc, SPHERICAL_COEFCOEF);
                transformations.add(new SphericalTransformation(scale));
            }
            case "horseshoe" -> {
                log.info("Для HorseshoeTransformation не требуется ввод параметров.");
                transformations.add(new HorseshoeTransformation());
            }
            case "popcorn" -> {
                log.info("Введите параметры a и b через пробел (например: 0.5 0.1): >>> ");
                double[] params = getDoubleArrayInput(sc, POPCORN_COEF);
                log.info("Введите цвeт (напримeр: BLUE, RED, GREEN): >>> ");
                Color color = getColorInput(sc);
                transformations.add(new PopcornTransformation(params[ONE - 1], params[ONE], color));
            }
            case "sinusoidal" -> {
                log.info("Введите цвет (например: BLUE, RED, GREEN): >>> ");
                Color color = getColorInput(sc);
                transformations.add(new SinusoidalTransformation(color));
            }
            case "heart" -> {
                log.info("Для HeartTransformation не требуется ввод параметров.");
                transformations.add(new HeartTransformation());
            }
            default -> log.warn("Неизвестный тип трансформации. Она не будет добавлена.");
        }
    }

    public static double[] getDoubleArrayInput(Scanner sc, int length) {
        double[] params = new double[length];
        try {
            String[] input = sc.nextLine().trim().split("\\s+");
            for (int i = 0; i < length; i++) {
                params[i] = Double.parseDouble(input[i]);
            }
        } catch (Exception e) {
            log.warn("Некорректный ввод. Используются значения по умолчанию.");
            Arrays.fill(params, 1.0); // Значения по умолчанию
        }
        return params;
    }
}
