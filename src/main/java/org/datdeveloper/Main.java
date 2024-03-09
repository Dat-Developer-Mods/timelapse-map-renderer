package org.datdeveloper;

import org.datdeveloper.background.BasicColourBackgroundProvider;
import org.datdeveloper.camera.SimpleCamera;
import org.datdeveloper.map_provider.SquareMapSource;
import org.datdeveloper.util.FVec2;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(final String[] args) throws IOException {
        System.out.println("Hello world!");

        new DatTimelapse()
                .setBackgroundProvider(BasicColourBackgroundProvider.getDefault())
                .setCamera(new SimpleCamera(new FVec2(304, 0), 5, "minecraft_overworld"))
                .setMapProvider(new SquareMapSource(Path.of("/home/jacob/Games/Servers/Minecraft/Hardcore/squaremap/web/tiles/")))
                .render();
        System.out.println("Test");
    }
}