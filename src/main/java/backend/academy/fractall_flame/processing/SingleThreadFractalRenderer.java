package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.ColorGradient;
import backend.academy.fractall_flame.config.Rect;
import backend.academy.fractall_flame.transformations.Transformation;
import java.util.List;

@SuppressWarnings({"PREDICTABLE_RANDOM", "ParameterNumber"})
public class SingleThreadFractalRenderer extends DefaultFractalRenderer {
    public SingleThreadFractalRenderer(long seed) {
        super(seed);
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        ColorGradient colorGradient
    ) {

        int start = 0;
        processing(canvas, world, variations, iterPerSample, symmetry, colorGradient, start, samples);

        BrightnessNormalizer.normalize(canvas);
        return canvas;
    }
}
