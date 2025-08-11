package io.github.blockneko11.nekocore.data.api;

import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;

@FunctionalInterface
public interface DataProviderFactory<T extends DataProvider> {
    T create(String modId, boolean validate, DataOutput output);
}
