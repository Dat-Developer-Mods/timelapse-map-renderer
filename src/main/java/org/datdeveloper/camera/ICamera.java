package org.datdeveloper.camera;

import org.datdeveloper.util.FPos;

/**
 * An interface representing information about the camera
 */
public interface ICamera {
    /**
     * Get the position of the camera
     * @param frame The current frame being rendered
     * @return The position of the camera at the given frame
     */
    FPos getCameraPosition(int frame);

    /**
     * Get the zoom of the camera
     * @param frame The current frame being rendered
     * @return The zoom of the camera at the given frame
     */
    float getCameraZoom(int frame);

    /**
     * Get the dimension of the camera
     *
     * @param frame The current frame being rendered
     * @return The dimension the camera is in at the given frame
     */
    String getCameraDimension(int frame);
}
