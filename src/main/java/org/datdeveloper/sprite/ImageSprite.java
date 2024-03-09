package org.datdeveloper.sprite;

import org.datdeveloper.camera.ICamera;
import org.datdeveloper.util.FVec2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSprite extends ISprite {
    BufferedImage spriteImage;

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     * @param spriteImage The image that the sprite draws
     */
    public ImageSprite(final FVec2 position, final float rotation, final FVec2 scale, final BufferedImage spriteImage) {
        super(position, rotation, scale);
        this.spriteImage = spriteImage;
    }

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     * @param centreOrigin Whether to centre the origin of the sprite
     * @param spriteImage The image that the sprite draws
     */
    public ImageSprite(final FVec2 position, final float rotation, final FVec2 scale, final boolean centreOrigin, final BufferedImage spriteImage) {
        super(position, rotation, scale, centreOrigin);
        this.spriteImage = spriteImage;
    }

    /**
     * @param position The position of the sprite
     * @param rotation The rotation of the sprite
     * @param scale The scale of the sprite
     * @param centreOrigin Whether to centre the origin of the sprite
     * @param drawMode The {@linkplain ESpriteDrawMode draw mode} of the sprite
     * @param spriteImage The image that the sprite draws
     */
    public ImageSprite(final FVec2 position, final float rotation, final FVec2 scale, final boolean centreOrigin, final ESpriteDrawMode drawMode, final BufferedImage spriteImage) {
        super(position, rotation, scale, centreOrigin, drawMode);
        this.spriteImage = spriteImage;
    }

    /** {@inheritDoc} */
    @Override
    public int getSpriteWidth() {
        return spriteImage.getWidth();
    }

    /** {@inheritDoc} */
    @Override
    public int getSpriteHeight() {
        return spriteImage.getHeight();
    }

    public BufferedImage getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(final BufferedImage spriteImage) {
        this.spriteImage = spriteImage;
    }

    /** {@inheritDoc} */
    @Override
    public void drawSprite(final Graphics2D frameImage, final int frame, final ICamera camera) {
        frameImage.drawImage(spriteImage, 0, 0, null);
    }
}
