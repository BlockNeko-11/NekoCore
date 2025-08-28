package io.github.blockneko11.nekocore.util.forge;

import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashMap;
import java.util.Map;

public final class ModEventBuses {
    private static final Map<String, IEventBus> BUSES = new HashMap<>();

    public static IEventBus get(String modId) {
        if (!contains(modId)) {
            throw new IllegalArgumentException("No event bus for mod id: " + modId);
        }

        return BUSES.get(modId);
    }

    public static boolean contains(String modId) {
        return BUSES.containsKey(modId);
    }

    public static void register(String modId, IEventBus bus) {
        BUSES.put(modId, bus);
    }
}
