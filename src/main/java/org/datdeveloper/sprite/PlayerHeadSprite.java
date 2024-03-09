package org.datdeveloper.sprite;

import org.datdeveloper.util.FVec2;

import java.awt.image.BufferedImage;
import java.util.UUID;

public class PlayerHeadSprite extends ImageSprite {
    UUID playerId;
    public PlayerHeadSprite(final FVec2 position, final float rotation, final FVec2 scale, final UUID playerId) {
        super(position, rotation, scale, true, ESpriteDrawMode.RELATIVE_TO_MAP_FIXED_SCALE, getPlayerHeadFromUUID(playerId));
        this.playerId = playerId;
    }

    static BufferedImage getPlayerHeadFromUUID(final UUID playerId) {

    }
}
