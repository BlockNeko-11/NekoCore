package io.github.blockneko11.nekocore.data.api;

import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface RegistryDependentDataProviderFactory<T extends DataProvider> {
    T create(String modId, boolean validate, DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture);
}
