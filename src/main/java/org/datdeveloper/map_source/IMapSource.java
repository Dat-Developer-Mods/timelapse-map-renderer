package org.datdeveloper.map_source;

import org.datdeveloper.util.FPos;
import org.datdeveloper.util.IPos;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An interface representing a source for a map
 */
@FunctionalInterface
public interface IMapSource {
    /**
     * Render the map onto the frame
     * @param frameImage The frame being rendered to
     * @param width The width of the frame
     * @param height The height of the frame
     * @param dimension The dimension
     * @param centre The centre position of the frame on the map
     * @param zoom The zoom level
     */
    void renderFrame(final Graphics2D frameImage, final int width, final int height, final String dimension, final FPos centre, final float zoom);
}
