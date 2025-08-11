package io.github.blockneko11.nekocore.data;

import io.github.blockneko11.nekocore.data.api.DataProviderFactory;
import io.github.blockneko11.nekocore.data.api.RegistryDependentDataProviderFactory;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NekoDataGeneration {
    private final String modId;
    private final boolean validate;
    private final DataGenerator.Pack pack;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;

    private final List<DataProviderFactory<?>> providers = new ArrayList<>();
    private final List<RegistryDependentDataProviderFactory<?>> registryDependentProviders = new ArrayList<>();

    public NekoDataGeneration(String modId, boolean validate, DataGenerator.Pack pack, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        this.modId = modId;
        this.validate = validate;
        this.pack = pack;
        this.registriesFuture = registriesFuture;
    }

    public <T extends DataProvider> void addProvider(DataProviderFactory<T> provider) {
        this.providers.add(provider);
    }

    public <T extends DataProvider> void addProvider(RegistryDependentDataProviderFactory<T> provider) {
        this.registryDependentProviders.add(provider);
    }

    public void run() {
        this.providers.forEach(provider -> {
            this.pack.addProvider(output -> provider.create(this.modId, this.validate, output));
        });

        this.registryDependentProviders.forEach(provider -> {
            this.pack.addProvider(output -> provider.create(this.modId, this.validate, output, this.registriesFuture));
        });
    }
}
