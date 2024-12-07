package backend.academy.fractall_flame.processing;

import backend.academy.fractall_flame.config.Pixel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BrightnessNormalizer {
    private static final double GAMMA = 2.2;
    private static final int YELLOWORANGE = 215;
    private static final int REDPIGMENT = 80;

    public static void normalize(FractalImage image) {
        int maxHitCount = getMaxHitCount(image);

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.pixel(x, y);
                int normalizedRed = (int) ((pixel.hitCount() * GAMMA) / maxHitCount);
                int normalizedGreen = (pixel.hitCount() * YELLOWORANGE) / maxHitCount;
                int normalizedBlue = (pixel.hitCount() * REDPIGMENT) / maxHitCount;
                image.setPixel(x, y, new Pixel(normalizedRed, normalizedGreen, normalizedBlue, pixel.hitCount()));
            }
        }
    }


    static int getMaxHitCount(FractalImage image) {
        int maxHitCount = 0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                maxHitCount = Math.max(maxHitCount, image.pixel(x, y).hitCount());
            }
        }
        return maxHitCount;
    }
}
