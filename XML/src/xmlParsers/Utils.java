package xmlParsers;

/**
 * Класс дополнительных утилит
 *
 * @author Artem Mukhin #6209
 */
@SuppressWarnings("WeakerAccess")
public class Utils {
    /**
     * Машинное эпсилон
     */
    public static final double EPS = machineEps();

    /**
     * Функция находящая машинное эпсилон для текущего ПК
     *
     * @return - EPS
     */
    private static double machineEps() {
        double eps = 1.0;
        while (1.0 + 0.5 * eps != 1.0) {
            eps *= 0.5;
        }
        return eps;
    }
}