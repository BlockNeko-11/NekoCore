package com.example.testmod.data;

import com.example.testmod.registry.TestRegistry;
import io.github.blockneko11.nekocore.data.provider.tag.NekoTagProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TestItemTagProvider extends NekoTagProvider.ItemTagProvider {
    public TestItemTagProvider(String modId, boolean validate, DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(modId, validate, output, registriesFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getTagBuilder(TestRegistry.TEST_ITEM_TAG)
                .replace(true)
                .add(TestRegistry.TEST_ITEM_1.get())
                .add(TestRegistry.TEST_ITEM_2.get());
    }
}
