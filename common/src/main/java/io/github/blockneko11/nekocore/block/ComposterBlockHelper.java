package io.github.blockneko11.nekocore.block;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;

public final class ComposterBlockHelper {
    public static void add(float chance, Item item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, chance);
    }

    public static void remove(Item item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.removeFloat(item);
    }

    public static void get(Item item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.getFloat(item);
    }
}
