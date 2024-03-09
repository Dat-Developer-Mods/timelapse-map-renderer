package org.datdeveloper.map_provider.tiles;

import org.datdeveloper.util.IVec2;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * A tile set for a given scale
 */
public final class BasicTileStore extends ITileStore {
    /** The stored tiles */
    private final Map<IVec2, BufferedImage> tiles;

    /**
     * @param scale The scale of the tile set
     * @param tiles The tiles to store
     */
    public BasicTileStore(final int scale, final Map<IVec2, BufferedImage> tiles) {
        super(scale);
        this.tiles = tiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getTile(final IVec2 position) {
        return tiles.get(position);
    }
}
