package org.datdeveloper.sprite;

import org.datdeveloper.camera.ICamera;
import org.datdeveloper.util.FVec2;
import org.datdeveloper.util.IRenderable;
import org.datdeveloper.util.IVec2;
import org.datdeveloper.util.MathUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

/**
 * An interface representing a sprite that can be drawn on the map
 */
public abstract class ISprite implements IRenderable {
    /**
     * The position of the sprite
     * <p>
     * When {@link #drawMode} is {@link ESpriteDrawMode#RELATIVE_TO_CANVAS}, this represents a pixel position of the
     * sprite in the frame. Otherwise, this represents a coordinate on the map (where {@link FVec2#y()} is the z
     * coordinate).
     * @see #drawMode
     */
    FVec2 position;

    /** The rotation of the sprite */
    float rotation;

    /** The scale of the sprite */
    FVec2 scale;

    /**
     * Used to order sprites, a higher z-index means the sprite is drawn later, or "in front" of sprites with a lower
     * z-index
     */
    int zIndex = 100;

    /**
     * How to interpret the position
     * <p>
     * When {@link ESpriteDrawMode#RELATIVE_TO_CANVAS}, the position should be interpreted as a pixel position in the
     * frame. Otherwise, the position should be interpreted as a coordinate on the map (where {@link FVec2#y()} is the z
     * coordinate).
     * @see #position
     */
    ESpriteDrawMode drawMode;

    /**
     * Whether to centre the origin of the sprite
     * <p>
     * By default, the origin is in the top left corner
     */
    boolean centreOrigin;

    /* -------------------------------------------- */
    /* Constructors                                 */
    /* -------------------------------------------- */

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     */
    protected ISprite(final FVec2 position, final float rotation, final FVec2 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.centreOrigin = false;
        this.drawMode = ESpriteDrawMode.RELATIVE_TO_MAP_FIXED_SCALE;
    }

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     * @param centreOrigin Whether to centre the origin of the sprite
     */
    protected ISprite(final FVec2 position, final float rotation, final FVec2 scale, final boolean centreOrigin) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.centreOrigin = centreOrigin;
        this.drawMode = ESpriteDrawMode.RELATIVE_TO_MAP_FIXED_SCALE;
    }

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     * @param centreOrigin Whether to centre the origin of the sprite
     * @param drawMode The {@linkplain ESpriteDrawMode draw mode} of the sprite
     */
    protected ISprite(final FVec2 position, final float rotation, final FVec2 scale, final boolean centreOrigin, final ESpriteDrawMode drawMode) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.centreOrigin = centreOrigin;
        this.drawMode = drawMode;
    }

    /* -------------------------------------------- */
    /* Getters/Setters                              */
    /* -------------------------------------------- */

    /**
     * Get the width of the sprite
     * <p>
     * Used for origin centre correction
     * @return The width of the sprite
     */
    public abstract int getSpriteWidth();

    /**
     * Get the height of the sprite
     * <p>
     * Used for origin centre correction
     * @return The height of the sprite
     */
    public abstract int getSpriteHeight();

    public FVec2 getPosition() {
        return position;
    }

    public void setPosition(final FVec2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(final float rotation) {
        this.rotation = rotation;
    }

    public FVec2 getScale() {
        return scale;
    }

    public void setScale(final FVec2 scale) {
        this.scale = scale;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(final int zIndex) {
        this.zIndex = zIndex;
    }

    public ESpriteDrawMode getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(final ESpriteDrawMode drawMode) {
        this.drawMode = drawMode;
    }

    public boolean isCentreOrigin() {
        return centreOrigin;
    }

    public void setCentreOrigin(final boolean centreOrigin) {
        this.centreOrigin = centreOrigin;
    }

    /* -------------------------------------------- */
    /* Rendering                                    */
    /* -------------------------------------------- */

    /** {@inheritDoc} */
    @Override
    public void render(final Graphics2D frameImage, final IVec2 frameDimensions, final int frame, final ICamera camera) {
        if (drawMode == ESpriteDrawMode.RELATIVE_TO_CANVAS) {
            renderCanvasSprite(frameImage, frameDimensions, frame, camera);
        } else {
            renderMapSprite(frameImage, frameDimensions, frame, camera);
        }
    }

    /**
     * Render a sprite to the frame in frame space
     * @param frameImage The frame being rendered to
     * @param frameDimensions The dimensions of the frame
     * @param frame The current frame being rendered
     * @param camera The camera for the scene
     */
    protected void renderCanvasSprite(final Graphics2D frameImage, final IVec2 frameDimensions, final int frame, final ICamera camera) {
        final AffineTransform transform = new AffineTransform();

        // Origin Correction
        if (centreOrigin) transform.translate((float) getSpriteWidth() / 2, (float) getSpriteHeight() / 2);

        transform.scale(scale.x(), scale.y());
        transform.rotate(rotation);
        transform.translate(position.x(), position.y());

        // Transform canvas and pass to subclass to draw
        frameImage.transform(transform);
        drawSprite(frameImage, frame, camera);
    }

    /**
     * Render a sprite to the frame in map space
     * @param frameImage The frame being rendered to
     * @param frameDimensions The dimensions of the frame
     * @param frame The current frame being rendered
     * @param camera The camera for the scene
     */
    protected void renderMapSprite(final Graphics2D frameImage, final IVec2 frameDimensions, final int frame, final ICamera camera) {
        final AffineTransform transform = new AffineTransform();
        final float cameraZoom = camera.getCameraZoom(frame);

        final FVec2 spritePos = MathUtil.worldPosToPixelAtZoom(position, cameraZoom);
        final FVec2 scaledViewCentre = MathUtil.worldPosToPixelAtZoom(camera.getCameraPosition(frame), cameraZoom);
        final FVec2 offset = frameDimensions.divide(2);

        // Origin Correction
        if (centreOrigin) {
            if (drawMode == ESpriteDrawMode.RELATIVE_TO_MAP_FIXED_SCALE) {
                transform.translate((float) -getSpriteWidth() / 2, (float) -getSpriteHeight() / 2);
            } else {
                final FVec2 spriteScaledSize = new FVec2(getSpriteWidth() * scale.x() * cameraZoom, getSpriteHeight() * scale.y() * cameraZoom);
                transform.translate(-spriteScaledSize.x() / 2, -spriteScaledSize.y() / 2);
            }
        }

        transform.translate(spritePos.x() + offset.x() - scaledViewCentre.x(), spritePos.y() + offset.y() - scaledViewCentre.y());


        transform.scale(scale.x(), scale.y());

        // Scale for viewport
        if (drawMode == ESpriteDrawMode.RELATIVE_TO_MAP) {
            transform.scale(cameraZoom, cameraZoom);
        }

        transform.rotate(rotation);

        // Transform canvas and pass to subclass to draw
        frameImage.transform(transform);
        drawSprite(frameImage, frame, camera);
    }

    /**
     * Draw the sprite
     * <p>
     * The canvas has been pre-transformed for the position, rotation, and scale. This method just needs to call the
     * relevant draw method, for example: {@link Graphics2D#drawImage(Image, int, int, ImageObserver)} or
     * {@link Graphics2D#drawRect(int, int, int, int)}
     * @param frameImage The image to draw to, pre-transformed
     * @param frame The current frame being rendered
     * @param camera The camera for the scene
     */
    public abstract void drawSprite(final Graphics2D frameImage, final int frame, final ICamera camera);
}
