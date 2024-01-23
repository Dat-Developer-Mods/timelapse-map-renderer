package org.datdeveloper;

import org.datdeveloper.map_source.SquareMapSource;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        new DatTimelapse()
                .setMapSource(new SquareMapSource(Path.of("/run/media/jacob/Jacobs USB/Hardcore/squaremap/web/tiles/minecraft_overworld/")))
                .render();
        System.out.println("Test");
    }
}