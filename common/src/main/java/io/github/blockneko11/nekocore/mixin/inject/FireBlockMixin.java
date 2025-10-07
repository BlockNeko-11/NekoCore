package io.github.blockneko11.nekocore.mixin.inject;

import io.github.blockneko11.nekocore.block.FireBlockHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {
    @Redirect(
            method = "getBurnChance(Lnet/minecraft/block/BlockState;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/objects/Object2IntMap;getInt(Ljava/lang/Object;)I",
                    remap = false
            )
    )
    private int nekocore$getBurnChance(Object2IntMap<Block> instance, Object o) {
        return FireBlockHelper.getEntries().get(o).getBurnChance();
    }

    @Redirect(
            method = "getSpreadChance",
            at = @At(
                    value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/objects/Object2IntMap;getInt(Ljava/lang/Object;)I",
                    remap = false
            )
    )
    private int nekocore$getSpreadChance(Object2IntMap<Block> instance, Object o) {
        return FireBlockHelper.getEntries().get(o).getSpreadChance();
    }
}
