package io.github.blockneko11.nekocore.item.tool.forge;

import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ToolInteractionHelperImpl {
    public static void addTillable(Block input,
                                   Predicate<ItemUsageContext> predicate,
                                   Consumer<ItemUsageContext> action) {
        MinecraftForge.EVENT_BUS.<BlockEvent.BlockToolModificationEvent>addListener(e -> {
            ItemUsageContext ctx = e.getContext();
            if (ToolActions.HOE_TILL == e.getToolAction() &&
                    ctx.getStack().canPerformAction(ToolActions.HOE_TILL) &&
                    e.getState().isOf(input) &&
                    predicate.test(ctx)) {
                if (!e.isSimulated()) {
                    action.accept(ctx);
                }

                e.setFinalState(ctx.getWorld().getBlockState(ctx.getBlockPos()));
            }
        });
    }
}
