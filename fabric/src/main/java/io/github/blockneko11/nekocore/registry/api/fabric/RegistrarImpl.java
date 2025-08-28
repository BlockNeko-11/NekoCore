package io.github.blockneko11.nekocore.registry.api.fabric;

import io.github.blockneko11.nekocore.registry.api.Registrar;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.function.Supplier;

public final class RegistrarImpl extends Registrar {
    private RegistrarImpl(String modId) {
        super(modId);
    }

    @Override
    public <R, T extends R> Supplier<T> register(RegistryKey<? extends Registry<T>> registry,
                                                 String id,
                                                 T entry) {
        Registry<R> reg = (Registry<R>) Registries.REGISTRIES.get((RegistryKey) registry);

        if (reg == null) {
            throw new IllegalArgumentException("Unknown registry: " + registry);
        }

        Registry.register(reg, this.createId(id), entry);
        return () -> entry;
    }

    @Override
    protected void onRegister() {
    }

    public static Registrar create(String modId) {
        return new RegistrarImpl(modId);
    }
}
