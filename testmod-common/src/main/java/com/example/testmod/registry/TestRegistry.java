package com.example.testmod.registry;

import com.example.testmod.TestMod;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.blockneko11.nekoplatform.registry.arch.DeferredRegisterManager;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public final class TestRegistry {
    public static final DeferredRegisterManager REGISTER = new DeferredRegisterManager(TestMod.MOD_ID);

    public static final RegistrySupplier<Item> TEST_ITEM = REGISTER.register(
            Registries.ITEM, "test_item", () -> new Item(new Item.Settings()));

    public static void init() {
        REGISTER.register();
    }
}
