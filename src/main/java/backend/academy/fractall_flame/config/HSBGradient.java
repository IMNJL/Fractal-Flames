package backend.academy.fractall_flame.config;

import java.awt.Color;

public class HSBGradient implements ColorGradient {
    private static final float SATURATION = 1.0f;
    private static final double STEP = 200.0;
    private static final double HALF = 0.5;
    private static final float HALFF = 0.5f;

    @Override
    public Color apply(double x, double y, int iterations) {
        double normalizedIter = Math.min(1.0, iterations / STEP);
        float hue = (float) ((x * y * HALF + normalizedIter) % 1.0);
        float brightness = Math.max(HALFF, (float) Math.sqrt(iterations / (STEP / 2)));
        return Color.getHSBColor(hue, SATURATION, brightness);
    }
}
