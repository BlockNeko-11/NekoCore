package com.example.testmod.item;

import com.example.testmod.TestMod;
import io.github.blockneko11.nekocore.util.schedule.TaskScheduler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            return TypedActionResult.success(stack);
        }

        TaskScheduler.runTaskLater(() -> {
            TestMod.LOGGER.info("Delayed task executed!");
        }, 5);
        TestMod.LOGGER.info("Task started!");
        return TypedActionResult.consume(stack);
    }
}
