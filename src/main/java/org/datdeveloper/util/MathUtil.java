package org.datdeveloper.util;

import java.awt.geom.AffineTransform;

public class MathUtil {
    public static double roundAwayFromZero(final double number) {
        return number > 0 ? Math.ceil(number) : Math.floor(number);
    }
    public static double roundTowardZero(final double number) {
        return number < 0 ? Math.ceil(number) : Math.floor(number);
    }

    public static FVec2 worldPosToPixelAtZoom(final FVec2 position, final float zoom) {
        return position.divide((float) Math.pow(2, 3 - zoom));
    }
}
