package io.github.blockneko11.nekocore.item.tool.fabric;

import com.mojang.datafixers.util.Pair;
import io.github.blockneko11.nekocore.mixin.fabric.access.HoeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ToolInteractionHelperImpl {
    public static void addTillable(Block input,
                                   Predicate<ItemUsageContext> predicate,
                                   Consumer<ItemUsageContext> action) {
        HoeItemAccessor.getTillingActions().put(input, Pair.of(predicate, action));
    }
}
