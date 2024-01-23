package org.datdeveloper.util;

/**
 * @param x The x position
 * @param y The y position
 */
public record IPos(int x, int y) {
    public FPos toFPos() {
        return new FPos(x, y);
    }
}