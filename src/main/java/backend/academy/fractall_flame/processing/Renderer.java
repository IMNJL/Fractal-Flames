package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.util.List;

public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        ColorGradient colorGradient
    );
}
