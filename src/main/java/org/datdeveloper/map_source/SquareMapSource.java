package org.datdeveloper.map_source;

import org.datdeveloper.map_source.tiles.BasicTileStore;
import org.datdeveloper.map_source.tiles.ITileStore;
import org.datdeveloper.map_source.tiles.MultiScaleTileStore;
import org.datdeveloper.util.FPos;
import org.datdeveloper.util.IPos;
import org.datdeveloper.util.MathUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A map source representing maps from the <a href="https://modrinth.com/plugin/squaremap">Square Map Mod</a>
 */
public class SquareMapSource implements IMapSource {
    /**
     * A regex pattern for the tile file names
     * Group 1: The X Coord
     * Group 2: The Z Coord
     */
    private static final Pattern TILE_COORD_PATTERN = Pattern.compile("(-?\\d+)_(-?\\d+)");

    /** The resolution of each tile */
    private static final int TILE_RESOLUTION = 512;


    /**
     * A store of map tiles for different dimensions
     */
    Map<String, MultiScaleTileStore> dimensionTiles;

    /**
     * Load a square map source using the tiles directly from the disk
     * @param path The path to square map's tiles folder (Typically "./squaremap/web/tiles/")
     * @throws IOException Thrown when the path doesn't exist, or isn't a directory
     */
    public SquareMapSource(final Path path) throws IOException {
        dimensionTiles = new HashMap<>();

        try (final Stream<Path> scalesStream = Files.list(path)) {
            scalesStream
                    .filter(Files::isDirectory)
                    .forEach(this::processDimensionDir);
        }
    }

    /**
     * Parse a dimension in the tiles directory
     * @param dimDir The directory of the dimension
     */
    private void processDimensionDir(final Path dimDir) {
        final String dimensionName = dimDir.getFileName().toString();

        try (final Stream<Path> scalesStream = Files.list(dimDir)) {
            scalesStream
                    .filter(Files::isDirectory)
                    .forEach(dimensionPath -> {
                        dimensionTiles.put(dimensionName, new MultiScaleTileStore());
                        processScaleDir(dimensionName, dimensionPath);
                    });
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to parse dimension directory: %s".formatted(dimDir), e);
        }
    }

    /**
     * Parse a scale in a dimension's directory
     * @param dimension The dimension the scale directory is in
     * @param scaleDir The directory of the scale level
     */
    private void processScaleDir(final String dimension, final Path scaleDir) {
        final int scale = Integer.parseInt(scaleDir.getFileName().toString());
        try (final Stream<Path> tileFileStream = Files.list(scaleDir)){
            final Map<IPos, BufferedImage> tiles = tileFileStream
                    .collect(Collectors.toMap(tileFile -> {
                        final String filename = com.google.common.io.Files.getNameWithoutExtension(tileFile.getFileName().toString());
                        final Matcher match = TILE_COORD_PATTERN.matcher(filename);
                        if (!match.matches()) {
                            throw new RuntimeException("Failed to parse tile name: %s".formatted(filename));
                        }

                        return new IPos(Integer.parseInt(match.group(1)), Integer.parseInt(match.group(2)));
                    }, tileFile -> {
                        try {
                            return ImageIO.read(tileFile.toFile());
                        } catch (final IOException e) {
                            throw new UncheckedIOException("Failed to open tile: %s".formatted(tileFile), e);
                        }
                    }));

            dimensionTiles.get(dimension).addTileScale(new BasicTileStore(scale, tiles));
        } catch (final IOException e) {
            throw new RuntimeException("Failed parse scale directory: %s".formatted(scaleDir), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void renderFrame(final Graphics2D frameImage, final int width, final int height, final String dimension, final FPos centre, final float zoom) {
        final ITileStore tiles = dimensionTiles.get(dimension).getOptimalTileScale(zoom);

        final FPos offset = new FPos((float) width / 2, (float) height / 2);

        final float realZoom = zoom - tiles.scale;
        final double scale = Math.pow(2, realZoom);
        final float scaledResolution = (float) (TILE_RESOLUTION * scale);

        final FPos scaledCentre = centre.divide((float) Math.pow(2, 3 - zoom));

        final int minTileX = (int) MathUtil.roundAwayFromZero((centre.x() - offset.x()) / scaledResolution);
        final int minTileY = (int) MathUtil.roundAwayFromZero((centre.y() - offset.y()) / scaledResolution);
        final int maxTileX = (int) MathUtil.roundAwayFromZero((centre.x() + offset.x()) / scaledResolution);
        final int maxTileY = (int) MathUtil.roundAwayFromZero((centre.y() + offset.y()) / scaledResolution);

        for (int tileX = minTileX; tileX <= maxTileX; tileX++) {
            for (int tileY = maxTileY; tileY >= minTileY; tileY--) {
                final BufferedImage tile = tiles.getTile(new IPos(tileX, tileY));
                if (tile == null) continue;

                final AffineTransform transform = new AffineTransform();
                transform.translate((tileX * scaledResolution + offset.x() - scaledCentre.x()), (tileY * scaledResolution + offset.y() - scaledCentre.y()));
                transform.scale(scale, scale);

                frameImage.drawImage(tile, transform, null);
            }
        }
    }
}
