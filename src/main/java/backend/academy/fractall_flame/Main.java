package backend.academy.fractall_flame;

import backend.academy.fractall_flame.config.FractalGenerator;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Main {

    public static void main(String[] args) {
        FractalGenerator generator = new FractalGenerator();
        generator.generateFractal();
    }
}
