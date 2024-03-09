package org.datdeveloper;

import org.datdeveloper.background.IBackgroundProvider;
import org.datdeveloper.camera.ICamera;
import org.datdeveloper.map_provider.IMapProvider;
import org.datdeveloper.sprite.ESpriteDrawMode;
import org.datdeveloper.sprite.ImageSprite;
import org.datdeveloper.tick.ITickHandler;
import org.datdeveloper.util.FVec2;
import org.datdeveloper.util.IRenderable;
import org.datdeveloper.util.IVec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A timelapse builder
 */
public class DatTimelapse {
    // Output Settings
    /** The number of frames to render */
    protected int frameCount = 100;

    /** The number of frames per second */
    protected int frameRate = 30;

    /** The width of each frame */
    protected IVec2 frameDimensions = new IVec2(1280, 720);

    // Data Providers
    /** The camera settings */
    protected ICamera camera;

    /** The background config */
    protected IBackgroundProvider backgroundProvider;

    /** The source for the map */
    protected IMapProvider mapProvider;

    protected List<ITickHandler> tickHandlers = new ArrayList<>();
    protected List<IRenderable> lateRenderers = new ArrayList<>();

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
        this.frameDimensions = new IVec2(width, height);

        return this;
    }

    /**
     * Set the resolution of the output frame
     * @param dimensions The new dimensions of the output frame
     * @return The DatTimelapse instance
     */
    public DatTimelapse setOutputResolution(final IVec2 dimensions) {
        this.frameDimensions = dimensions;

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
     * Set the camera
     * @param camera The new camera
     * @return The DatTimelapse instance
     */
    public DatTimelapse setCamera(final ICamera camera) {
        this.camera = camera;

        return this;
    }

    /**
     * Set the backgroundProvider used for rendering the background
     * @param backgroundProvider The new backgroundProvider
     * @return The DatTimelapse instance
     */
    public DatTimelapse setBackgroundProvider(final IBackgroundProvider backgroundProvider) {
        this.backgroundProvider = backgroundProvider;

        return this;
    }

    /**
     * Set the mapProvider used for rendering the map
     * @param mapProvider The new mapProvider
     * @return The DatTimelapse instance
     */
    public DatTimelapse setMapProvider(final IMapProvider mapProvider) {
        this.mapProvider = mapProvider;

        return this;
    }

    /**
     * Register a method to be called at the beginning of every frame
     * <p>
     * This can be used to modify the state of the timelapse
     * @param tickHandler The tickHandler to register
     * @return The DatTimelapse instance
     */
    public DatTimelapse registerTickHandler(final ITickHandler tickHandler) {
        tickHandlers.add(tickHandler);

        return this;
    }

    public DatTimelapse registerLateRenderer(final IRenderable lateRenderer) {
        lateRenderers.add(lateRenderer);

        return this;
    }

    /* -------------------------------------------- */
    /* Getters                                      */
    /* -------------------------------------------- */

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public IVec2 getFrameDimensions() {
        return frameDimensions;
    }

    public ICamera getCamera() {
        return camera;
    }

    public IBackgroundProvider getBackgroundProvider() {
        return backgroundProvider;
    }

    public IMapProvider getMapProvider() {
        return mapProvider;
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
        Graphics2D graphic;

        // Call ticks
        tickHandlers.forEach(tickHandler -> tickHandler.tick(this, frame));

        // Setup Rendering
        final BufferedImage frameImage = new BufferedImage(frameDimensions.x(), frameDimensions.y(), BufferedImage.TYPE_INT_ARGB);

        // Render Background
        graphic = frameImage.createGraphics();
        backgroundProvider.render(graphic, frameDimensions, frame, camera);
        graphic.dispose();

        // Render map
        graphic = frameImage.createGraphics();
        mapProvider.render(graphic, frameDimensions, frame, camera);
        graphic.dispose();

        // Sprite rendering
        try {
            graphic = frameImage.createGraphics();
            graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            new ImageSprite(new FVec2(304, -3), 0, new FVec2(1, 1), true, ESpriteDrawMode.RELATIVE_TO_MAP, ImageIO.read(Path.of("/home/jacob/Games/Servers/Minecraft/Hardcore/squaremap/web/images/icon/spawn.png").toFile()))
                    .render(graphic, frameDimensions, frame, camera);
            graphic.dispose();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        // Late Rendering
        lateRenderers.forEach(lateRenderer -> {
            final Graphics2D rendererGraphic = frameImage.createGraphics();
            mapProvider.render(rendererGraphic, frameDimensions, frame, camera);
            rendererGraphic.dispose();
        });

        return frameImage;
    }

    /* -------------------------------------------- */
    /* Util                                         */
    /* -------------------------------------------- */

    private void ensureSetup() {
        if (camera == null) throw new RuntimeException("Failed to render, no camera has been configured.");
        if (backgroundProvider == null) throw new RuntimeException("Failed to render, no background provider has been configured");
        if (mapProvider == null) throw new RuntimeException("Failed to render, no map provider has been configured.");
    }
}
