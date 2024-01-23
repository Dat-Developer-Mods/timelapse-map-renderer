package org.datdeveloper.util;

public class MathUtil {
    public static double roundAwayFromZero(double number) {
        return number > 0 ? Math.ceil(number) : Math.floor(number);
    }
    public static double roundTowardZero(double number) {
        return number < 0 ? Math.ceil(number) : Math.floor(number);
    }
}
