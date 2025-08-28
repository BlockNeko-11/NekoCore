package com.example.testmod.registry;

import com.example.testmod.TestMod;
import com.example.testmod.item.TestItem;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.blockneko11.nekocore.registry.arch.DeferredRegisterManager;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TestRegistry {
    public static final DeferredRegisterManager REGISTER = new DeferredRegisterManager(TestMod.MOD_ID);

    public static final RegistrySupplier<Item> TEST_ITEM = REGISTER.register(
            Registries.ITEM, "test_item", () -> new TestItem(new Item.Settings()));

    public static final TagKey<Item> TEST_ITEM_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(TestMod.MOD_ID, "test_item_tag"));

    public static void init() {
        REGISTER.register();
    }
}
