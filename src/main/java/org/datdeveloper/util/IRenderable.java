package org.datdeveloper.util;

import org.datdeveloper.camera.ICamera;

import java.awt.*;

/** An interface for objects that can render onto the frame */
@FunctionalInterface
public interface IRenderable {
    /**
     * Render the object to the given frame
     * @param frameImage The frame being rendered to
     * @param frameDimensions The dimensions of the frame
     * @param frame The current frame being rendered
     * @param camera The camera for the scene
     */
    void render(final Graphics2D frameImage, final IVec2 frameDimensions, final int frame, final ICamera camera);
}
