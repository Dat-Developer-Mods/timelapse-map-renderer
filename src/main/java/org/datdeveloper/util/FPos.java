package org.datdeveloper.util;

/**
 * A 2d position stored as a float
 * @param x The x position
 * @param y The y position
 */
public record FPos(float x, float y) {
    public FPos add(FPos rhs) {
        return new FPos(x + rhs.x, y + rhs.y);
    }

    public FPos add(float x, float y) {
        return new FPos(this.x + x, this.y + y);
    }
    public FPos subtract(FPos rhs) {
        return new FPos(x - rhs.x, y - rhs.y);
    }

    public FPos subtract(float x, float y) {
        return new FPos(this.x - x, this.y - y);
    }

    public FPos multiply(float scalar) {
        return new FPos(this.x * scalar, this.y * scalar);
    }

    public FPos divide(float scalar) {
        return new FPos(this.x / scalar, this.y / scalar);
    }
    public IPos toIPos() {
        return new IPos((int) x, (int) y);
    }
}
