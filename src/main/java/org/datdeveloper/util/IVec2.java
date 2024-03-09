package org.datdeveloper.util;

/**
 * A 2d vector stored as an integer
 * @param x The x component
 * @param y The y component
 */
public record IVec2(int x, int y) {
    public FVec2 divide(final int scalar) {
        return new FVec2((float) x / scalar, (float) y / scalar);
    }

    public FVec2 toFVec2() {
        return new FVec2(x, y);
    }
}