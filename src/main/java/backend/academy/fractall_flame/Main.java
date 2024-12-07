package backend.academy.fractall_flame;

import backend.academy.fractall_flame.config.FractalGenerator;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 *  по времени работы одного и того же алгоритма
 *  1 поток - 57672 мс
 *  многопоток - 52076 мс
 *  многопоточное исполнение работает быстрее однопоточного
 * */

@Log4j2
@UtilityClass
public class Main {

    public static void main(String[] args) {
        FractalGenerator generator = new FractalGenerator();
        generator.generateFractal();
    }
}
