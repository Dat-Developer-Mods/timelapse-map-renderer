package org.datdeveloper.sprite;

/**
 * The draw mode of the sprite
 */
public enum ESpriteDrawMode {
    /**
     * Draw the sprite relative to the map
     * <p>
     * The position of the sprite is a position in the map world, and the size of the sprite is scaled with the zoom
     */
    RELATIVE_TO_MAP,

    /**
     * Draw the sprite relative to the map, but with a scale that doesn't change with the zoom
     * <p>
     * The position of the sprite is a position in the map world, and the size of the sprite is not affected by the zoom
     */
    RELATIVE_TO_MAP_FIXED_SCALE,

    /**
     * Draw the sprite relative to the canvas
     * <p>
     * The position of the sprite is a position on the frame image, and the size of the sprite is not affected by the zoom
     * */
    RELATIVE_TO_CANVAS
}
