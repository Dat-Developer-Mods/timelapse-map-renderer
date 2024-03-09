package org.datdeveloper.background;

import org.datdeveloper.camera.ICamera;
import org.datdeveloper.util.IRenderable;
import org.datdeveloper.util.IVec2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BasicColourBackgroundProvider implements IBackgroundProvider {
    /** A mapping of dimensions to biomes */
    protected Map<String, Color> colourMap = new HashMap<>();

    /** The default colour when the dimension doesn't have an assigned colour */
    protected Color defaultColour = Color.gray;

    /**
     * Get an instance of the BasicColourBackground with the default dimension colours
     * @return An instance of BasicColourBackground with the default dimension colours
     */
    public static BasicColourBackgroundProvider getDefault() {
        final BasicColourBackgroundProvider backgroundProvider = new BasicColourBackgroundProvider();
        backgroundProvider.addDefaultDimensionColours();

        return backgroundProvider;
    }

    /**
     * Add to the BasicColourBackground instance background colour definitions for the default minecraft dimensions
     */
    public void addDefaultDimensionColours() {
        colourMap.put("minecraft_overworld", new Color(10, 130, 180));
        colourMap.put("minecraft_the_nether", new Color(120, 30, 20));
        colourMap.put("minecraft_the_end", new Color(65, 10, 90));
    }

    /**
     * Set the background colour for a dimension
     * @param dimension The dimension the colour is for
     * @param dimensionColour The new background colour for the dimension
     */
    public void setDimensionColor(final String dimension, final Color dimensionColour) {
        colourMap.put(dimension, dimensionColour);
    }

    /**
     * Set the default background colour used for dimensions without a defined background colour
     * @param defaultColour The new default background colour
     */
    public void setDefaultColour(final Color defaultColour) {
        this.defaultColour = defaultColour;
    }

    /**
     * Get the background colour for the dimension
     * <p>
     * If no colour has been provided for the dimension, then the default colour will be used
     * @param dimension The dimension to get the colour for
     * @return The colour for the dimension
     */
    protected Color getColour(final String dimension) {
        return colourMap.getOrDefault(dimension, defaultColour);
    }

    /** {@inheritDoc} */
    @Override
    public void render(final Graphics2D frameImage, final IVec2 frameDimensions, final int frame, final ICamera camera) {
        frameImage.setBackground(getColour(camera.getCameraDimension(frame)));
        frameImage.clearRect(0, 0, frameDimensions.x(), frameDimensions.y());
    }
}
