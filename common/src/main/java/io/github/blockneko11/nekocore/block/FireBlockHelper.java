package io.github.blockneko11.nekocore.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public final class FireBlockHelper {
    private static final Map<Block, Entry> ENTRIES = new HashMap<>();

    public static void add(Block block, int burnChance, int spreadChance) {
        ENTRIES.put(block, new Entry(burnChance, spreadChance));
    }

    public static void remove(Block block) {
        ENTRIES.put(block, null);
    }

    public static Map<Block, Entry> getEntries() {
        ENTRIES.entrySet().removeIf(entry -> entry.getValue() == null);
        return ENTRIES;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Entry {
        private final int burnChance;
        private final int spreadChance;
    }
}
