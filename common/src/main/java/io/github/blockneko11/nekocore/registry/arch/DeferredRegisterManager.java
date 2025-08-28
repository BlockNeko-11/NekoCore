package io.github.blockneko11.nekocore.registry.arch;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DeferredRegisterManager {
    private boolean initialized = false;

    private final String modId;
    private final Map<Identifier, DeferredRegister<?>> registers = new LinkedHashMap<>();

    public DeferredRegisterManager(String modId) {
        this.modId = modId;
    }

    public <R, T extends R> RegistrySupplier<T> registerInstance(RegistryKey<Registry<T>> registry,
                                                                 String id,
                                                                 T entry) {
        return this.register(registry, id, () -> entry);
    }

    public <R, T extends R> RegistrySupplier<T> register(RegistryKey<Registry<T>> registry,
                                                         String id,
                                                         Supplier<T> entry) {
        DeferredRegister<T> register = (DeferredRegister<T>) this.registers.computeIfAbsent(
                registry.getValue(),
                $ -> DeferredRegister.create(this.modId, registry));

        return register.register(id, entry);
    }

    public void register() {
        if (this.initialized) {
            throw new IllegalStateException("Cannot register twice");
        }

        this.initialized = true;
        this.registers.values().forEach(DeferredRegister::register);
    }
}
