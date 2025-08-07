package io.github.blockneko11.nekoplatform.registry.arch;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DeferredRegisterManager {
    private boolean initialized = false;

    private final String modId;
    private final Map<Registry<?>, DeferredRegister<?>> registers = new LinkedHashMap<>();

    public DeferredRegisterManager(String modId) {
        this.modId = modId;
    }

    public <T> DeferredRegister<T> create(Registry<T> registry) {
        return DeferredRegister.create(this.modId, (RegistryKey<Registry<T>>) registry.getKey());
    }

    private <T> DeferredRegister<T> getOrCreate(Registry<T> registry) {
        return (DeferredRegister<T>) this.registers.computeIfAbsent(
                registry,
                $ -> this.create(registry));
    }

    public <R, T extends R> RegistrySupplier<T> registerInstance(Registry<R> registry, String id, T entry) {
        return this.register(registry, id, () -> entry);
    }

    public <R, T extends R> RegistrySupplier<T> register(Registry<R> registry, String id, Supplier<T> entry) {
        return this.getOrCreate(registry).register(id, entry);
    }

    public void register() {
        if (this.initialized) {
            throw new IllegalStateException("cannot register twice");
        }

        this.initialized = true;
        this.registers.values().forEach(DeferredRegister::register);
    }
}
