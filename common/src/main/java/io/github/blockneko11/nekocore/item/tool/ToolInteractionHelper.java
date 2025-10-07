package io.github.blockneko11.nekocore.item.tool;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ToolInteractionHelper {
    public static void addStrippable(Block input, Block output) {
        AxeItemHooks.addStrippable(input, output);
    }

    public static void addFlattenable(Block input, BlockState result) {
        ShovelItemHooks.addFlattenable(input, result);
    }

    @ExpectPlatform
    public static void addTillable(Block input,
                                   Predicate<ItemUsageContext> predicate,
                                   Consumer<ItemUsageContext> action) {
        throw new AssertionError();
    }
}
