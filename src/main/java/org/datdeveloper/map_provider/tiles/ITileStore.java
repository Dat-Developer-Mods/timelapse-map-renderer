package org.datdeveloper.map_provider.tiles;

import org.datdeveloper.util.IVec2;

import java.awt.image.BufferedImage;

/**
 * An interface representing a store of map tiles of a specific zoom level
 */
public abstract class ITileStore {
    public final int scale;

    protected ITileStore(final int scale) {
        this.scale = scale;
    }

    /**
     * Get a tile at the given position.
     * @param position The position of the tile to get
     * @return The tile at the given position
     */
    public abstract BufferedImage getTile(IVec2 position);
}
