package org.datdeveloper.util;

/**
 * A 2d vector stored as a float
 * @param x The x component
 * @param y The y component
 */
public record FVec2(float x, float y) {
    public FVec2 add(final FVec2 rhs) {
        return new FVec2(x + rhs.x, y + rhs.y);
    }

    public FVec2 add(final float x, final float y) {
        return new FVec2(this.x + x, this.y + y);
    }
    public FVec2 subtract(final FVec2 rhs) {
        return new FVec2(x - rhs.x, y - rhs.y);
    }

    public FVec2 subtract(final float x, final float y) {
        return new FVec2(this.x - x, this.y - y);
    }

    public FVec2 multiply(final float scalar) {
        return new FVec2(this.x * scalar, this.y * scalar);
    }

    public FVec2 divide(final float scalar) {
        return new FVec2(this.x / scalar, this.y / scalar);
    }
    public IVec2 toIVec2() {
        return new IVec2((int) x, (int) y);
    }
}
