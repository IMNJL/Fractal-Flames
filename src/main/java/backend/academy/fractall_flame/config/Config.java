package backend.academy.fractall_flame.config;

import backend.academy.fractall_flame.transformations.HeartTransformation;
import backend.academy.fractall_flame.transformations.LinearTransformation;
import backend.academy.fractall_flame.transformations.PDJTransformation;
import backend.academy.fractall_flame.transformations.SphericalTransformation;
import backend.academy.fractall_flame.transformations.SwirlTransformation;
import backend.academy.fractall_flame.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
@UtilityClass
public class Config {
    public static final int RGB = 255;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int SAMPLES = 200_000;
    public static final int MILLISEC = 1_000_000;
    public static final short ITER_PER_SAMPLE = 150;
    public static final int SYMMETRY = 4;
    public static final long SEED = System.currentTimeMillis();
    public static final String OUTPUT_PATH = "src/main/resources/fractal_heart_12.png";

    public static final Rect WORLD = new Rect(-1.2, -1.5, 2.4, 3.0);
    public static final List<Transformation> TRANSFORMATIONS = List.of(
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

}
