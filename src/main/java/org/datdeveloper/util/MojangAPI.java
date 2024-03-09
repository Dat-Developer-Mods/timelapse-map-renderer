package org.datdeveloper.util;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MojangAPI {
    static Map<UUID, String> nameMap = new HashMap<>();

    public static String uuidToUsername(final UUID playerId) {
        return nameMap.computeIfAbsent(playerId, (uuid) -> {
            final HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.mojang.com/user/profile/" + uuid.toString()))
                    .GET()
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .build();
            request.
        });
    }
}
