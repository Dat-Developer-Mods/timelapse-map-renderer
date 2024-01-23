package org.datdeveloper.map_source.tiles;

import java.util.ArrayList;
import java.util.List;

/**
 * A container for tiles that handles multiple possible scales
 */
public class MultiScaleTileStore {
    List<ITileStore> tiles = new ArrayList<>();

    /**
     * Add a new tile set
     * <p>
     * If there is already a tile store at that scale, it will be replaced
     * @param newStore The new tile store to add
     */
    public void addTileScale(ITileStore newStore) {
        for (int i = 0; i < tiles.size(); i++) {
            ITileStore tileStore = tiles.get(i);
            if (tileStore.scale == newStore.scale) {
                tiles.set(i, newStore);
                return;
            } else if (tileStore.scale > newStore.scale) {
                tiles.add(i, newStore);
                return;
            }
        }

        tiles.add(newStore);
    }

    public ITileStore getOptimalTileScale(float targetScale) {
        if (tiles.isEmpty()) return null;

        ITileStore bestTile = tiles.get(0);
        for (final ITileStore tile : tiles) {
            // We want a higher resolution, as long as it's not one or more scale unit greater.
            if (tile.scale > targetScale) {
                return tile.scale - targetScale < 1 ? tile : bestTile;
            }
            bestTile = tile;
        }

        return bestTile;
    }

}

