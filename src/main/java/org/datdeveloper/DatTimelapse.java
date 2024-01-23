package org.datdeveloper;

import org.datdeveloper.camera.ICamera;
import org.datdeveloper.map_source.IMapSource;
import org.datdeveloper.util.FPos;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A timelapse builder
 */
public class DatTimelapse {
    // Output Settings
    /** The number of frames to render */
    private int frameCount = 100;

    /** The number of frames per second */
    private int frameRate = 30;

    /** The width of each frame */
    private int frameWidth = 1280;

    /** The height of each frame */
    private int frameHeight = 720;

    // Data Providers
    /** The camera settings */
    private ICamera camera;

    /** The source for the map */
    private IMapSource mapSource;

    /* -------------------------------------------- */
    /* Builder Setup                                */
    /* -------------------------------------------- */

    /* Output Setup ------------------------------- */

    /**
     * Set the resolution of the output frame
     * @param width The new width of the output frame
     * @param height The new height of the output frame
     * @return The DatTimelapse instance
     */
    public DatTimelapse setOutputResolution(final int width, final int height) {
        this.frameWidth = width;
        this.frameHeight = height;

        return this;
    }

    /**
     * Set the number of frames per second to render
     * @param frameRate The new frames per second
     * @return The DatTimelapse instance
     */
    public DatTimelapse setFrameRate(final int frameRate) {
        this.frameRate = frameRate;

        return this;
    }

    /**
     * Se the number of frames to render in total
     * <p>
     * Note: This overrides any value set with {@link #setRunTime(float)}
     * @param frameCount The new number of frames to render
     * @return The DatTimelapse instance
     * @see #setRunTime(float)
     */
    public DatTimelapse setFrameCountToRender(final int frameCount) {
        this.frameCount = frameCount;

        return this;
    }

    /**
     * Set the number of seconds to render
     * <p>
     * Note: This overrides any value set with {@link #setFrameCountToRender(int)}
     * @param seconds The new number of seconds to render
     * @return The DatTimelapse instance
     * @see #setFrameCountToRender(int)
     */
    public DatTimelapse setRunTime(final float seconds) {
        frameCount = (int) Math.ceil(seconds * frameRate);

        return this;
    }

    /* Data Setup --------------------------------- */

    /**
     * Set the source of the map
     * @param mapSource The new source of the map
     * @return The DatTimelapse instance
     */
    public DatTimelapse setMapSource(final IMapSource mapSource) {
        this.mapSource = mapSource;

        return this;
    }

    /* -------------------------------------------- */
    /* Rendering                                    */
    /* -------------------------------------------- */

    /**
     * Render out the sequence of frames
     */
    public void render() {
        ensureSetup();

        for(int frame = 0; frame < frameCount; frame++) {
            final BufferedImage renderedFrame = renderFrame(frame);

            // Write to disk / Pass to output handler
        }
    }

    /**
     * Render an individual frame
     * @param frame The frame to render
     * @return The rendered frame
     */
    protected BufferedImage renderFrame(final int frame) {
        // Temp Constants
        final FPos centre = new FPos(-323, 91);
        final float zoom = 5;
        final String world = "minecraft:overworld";

        // Setup Rendering
        final BufferedImage frameImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D graphic = frameImage.createGraphics();
        graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        // Render Background
        graphic.setColor(new Color(10, 130, 180));
        graphic.fillRect(0, 0, frameWidth, frameHeight);

        // Render map
        mapSource.renderFrame(graphic, frameWidth, frameHeight, world, centre, zoom);

        // Render Markers
        // Render Player Heads


        // Cleanup
        graphic.dispose();

        return frameImage;
    }

    /* -------------------------------------------- */
    /* Util                                         */
    /* -------------------------------------------- */

    private void ensureSetup() {
        if (camera == null) throw new RuntimeException("Failed to render, no camera has been configured.");
        if (mapSource == null) throw new RuntimeException("Failed to render, no map source has been configured.");
    }
}
