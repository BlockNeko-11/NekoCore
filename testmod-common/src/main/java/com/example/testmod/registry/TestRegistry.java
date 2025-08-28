package com.example.testmod.registry;

import com.example.testmod.TestMod;
import com.example.testmod.item.TestItem;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.blockneko11.nekocore.registry.api.Registrar;
import io.github.blockneko11.nekocore.registry.arch.DeferredRegisterManager;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public final class TestRegistry {
    public static final DeferredRegisterManager REGISTER = new DeferredRegisterManager(TestMod.MOD_ID);
    public static final RegistrySupplier<Item> TEST_ITEM_1 = REGISTER.register(
            RegistryKeys.ITEM, "test_item_1", () -> new TestItem(new Item.Settings()));


    public static final Registrar REGISTRAR = Registrar.create(TestMod.MOD_ID);
    public static final Supplier<Item> TEST_ITEM_2 = REGISTRAR.register(
            RegistryKeys.ITEM, "test_item_2", new TestItem(new Item.Settings()));

    public static final TagKey<Item> TEST_ITEM_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(TestMod.MOD_ID, "test_item_tag"));

    public static void init() {
        REGISTER.register();
        REGISTRAR.register();
    }
}
