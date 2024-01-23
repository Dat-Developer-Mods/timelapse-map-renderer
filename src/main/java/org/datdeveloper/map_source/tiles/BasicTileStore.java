package org.datdeveloper.map_source.tiles;

import org.datdeveloper.util.IPos;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * A tile set for a given scale
 */
public final class BasicTileStore extends ITileStore {
    /** The stored tiles */
    private final Map<IPos, BufferedImage> tiles;

    /**
     * @param scale The scale of the tile set
     * @param tiles The tiles to store
     */
    public BasicTileStore(final int scale, final Map<IPos, BufferedImage> tiles) {
        super(scale);
        this.tiles = tiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getTile(final IPos position) {
        return tiles.get(position);
    }
}
