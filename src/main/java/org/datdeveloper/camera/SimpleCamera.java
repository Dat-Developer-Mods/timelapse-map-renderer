package org.datdeveloper.camera;

import org.datdeveloper.util.FVec2;

/**
 * A camera implementation that remains static throughout the runtime of the timelapse
 */
public class SimpleCamera implements ICamera {
    private FVec2 cameraPosition;
    private float cameraZoom;
    private String cameraDimension;

    /**
     * @param cameraPosition The position of the camera
     * @param cameraZoom The zoom of the camera
     * @param cameraDimension The dimension the camera is in
     */
    public SimpleCamera(final FVec2 cameraPosition, final float cameraZoom, final String cameraDimension) {
        this.cameraPosition = cameraPosition;
        this.cameraZoom = cameraZoom;
        this.cameraDimension = cameraDimension;
    }

    /** {@inheritDoc} */
    @Override
    public FVec2 getCameraPosition(final int frame) {
        return cameraPosition;
    }

    /** {@inheritDoc} */
    @Override
    public float getCameraZoom(final int frame) {
        return cameraZoom;
    }

    /** {@inheritDoc} */
    @Override
    public String getCameraDimension(final int frame) {
        return cameraDimension;
    }

    /**
     * Set a new position for the camera
     * @param cameraPosition The new position of the camera
     */
    public void setCameraPosition(final FVec2 cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    /**
     * Set a new zoom for the camera
     * @param cameraZoom The new position of the camera
     */
    public void setCameraZoom(final float cameraZoom) {
        this.cameraZoom = cameraZoom;
    }

    /**
     * Set a new dimension for the camera
     * @param cameraDimension The new dimension the camera is in
     */
    public void setCameraDimension(final String cameraDimension) {
        this.cameraDimension = cameraDimension;
    }
}
