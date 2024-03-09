package org.datdeveloper.tick;

import org.datdeveloper.DatTimelapse;

/** An interface for ticking each frame */
@FunctionalInterface
public interface ITickHandler {
    void tick(DatTimelapse timelapse, int frame);
}
