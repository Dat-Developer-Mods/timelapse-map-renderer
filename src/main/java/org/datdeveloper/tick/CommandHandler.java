package org.datdeveloper.tick;

import org.datdeveloper.DatTimelapse;

import java.time.Instant;
import java.util.UUID;

public class CommandHandler implements ITickHandler {
    float timeDilation = 1;


    @Override
    public void tick(final DatTimelapse timelapse, final int frame) {

    }
}

class Command {
    Instant timestamp;
    UUID player;
    String commandName;

    public Command(final Instant timestamp, final UUID player, final String command) {
        this.timestamp = timestamp;
        this.player = player;
        this.commandName = command;
    }

    public Command(final String commandString) {
        final int timestampEnd = commandString.indexOf(" ");
        this.timestamp = Instant.parse(commandString.substring(0, timestampEnd));

        final int uuidEnd = commandString.indexOf(" ", timestampEnd + 1);
        this.player = UUID.fromString(commandString.substring(timestampEnd + 1, uuidEnd));

        final int commandEnd = commandString.indexOf(" ", uuidEnd + 1);
        this.commandName = commandString.substring(uuidEnd + 1, commandEnd);
    }
}