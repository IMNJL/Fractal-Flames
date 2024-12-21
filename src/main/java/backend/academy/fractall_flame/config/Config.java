package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.HeartTransformation;
import backend.academy.fractall_flame.transformations.LinearTransformation;
import backend.academy.fractall_flame.transformations.PDJTransformation;
import backend.academy.fractall_flame.transformations.SphericalTransformation;
import backend.academy.fractall_flame.transformations.SwirlTransformation;
import backend.academy.fractall_flame.transformations.Transformation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@UtilityClass
public class Config {
    public static final int RGB = 255;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int SAMPLES = 200_000;
    public static final int MILLISEC = 1_000_000;
    public static final short ITER_PER_SAMPLE = 150;
    public static final double HUNDRED = 100.0;
    public static final int LINEAR_COEF = 4;
    public static final int PDJ_COEF = 4;
    public static final double SWIRL_COEF = 0.8;
    public static final double SPHERICAL_COEFCOEF = 1.6;
    public static final int POPCORN_COEF = 2;
    public static final int THIRD_PARAM = 3;
    public static final int SYMMETRY = 6;
    public static final long SEED = System.currentTimeMillis();
    public static final String OUTPUT_PATH = "src/main/resources/fractal_heart_%s.png";

    public static final Rect WORLD = new Rect(-1.2, -1.5, 2.4, 3.0);
    public static final List<Transformation> DEFAULT_TRANSFORMATIONS = List.of(
        new HeartTransformation(),
        new PDJTransformation(0.7, 0.3, 0.5, 0.9),
        new SwirlTransformation(0.8),
        new SphericalTransformation(1.6),
        new LinearTransformation(0.9, -0.4, 0.4, 1.3)
    );

    public static final Color[] COLOR_PALETTE = {
        Color.RED, Color.BLUE, Color.GREEN, Color.PINK, Color.YELLOW,
        Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.WHITE
    };

    public static List<Transformation> getDefaultTransformations() {
        return new ArrayList<>(DEFAULT_TRANSFORMATIONS);
    }

    protected static int getIntInput(Scanner sc, int defaultValue) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            log.warn("Некорректный ввoд. Испoльзуется значение по умолчанию: {}", defaultValue);
            return defaultValue;
        }
    }

    protected static long getLongInput(Scanner sc, long defaultValue) {
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            log.warn("Некорректный ввод. Используется значение пo yмолчанию: {}", defaultValue);
            return defaultValue;
        }
    }

    protected static double getDoubleInput(Scanner sc, double defaultValue) {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            log.warn("Некорректный ввод. Используется значение по умолчанию: {}", defaultValue);
            return defaultValue;
        }
    }

    protected static Color getColorInput(Scanner sc) {
        String colorName = sc.nextLine().trim().toUpperCase();
        try {
            return (Color) Color.class.getField(colorName).get(null);
        } catch (Exception e) {
            log.warn("Некорректный цвет. Используется цвет по умолчанию: BLUE");
            return Color.BLUE; // Цвет по умолчанию
        }
    }

}
