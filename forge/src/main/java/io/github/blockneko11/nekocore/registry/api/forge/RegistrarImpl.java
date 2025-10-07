package io.github.blockneko11.nekocore.registry.api.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.blockneko11.nekocore.registry.api.Registrar;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class RegistrarImpl extends Registrar {
    private final Map<Identifier, DeferredRegister<?>> registers = new LinkedHashMap<>();

    private RegistrarImpl(String modId) {
        super(modId);
    }

    @Override
    public <R, T extends R> Supplier<T> register(RegistryKey<? extends Registry<T>> registry,
                                                 String id,
                                                 T entry) {
        DeferredRegister<R> register = (DeferredRegister<R>) this.registers.computeIfAbsent(
                registry.getValue(),
                $ -> DeferredRegister.create(registry, this.modId));

        return register.register(id, () -> entry);
    }

    @Override
    protected void onRegister() {
        IEventBus bus = EventBuses.getModEventBus(this.modId).orElseThrow();
        this.registers.values().forEach(r -> r.register(bus));
    }

    public static Registrar create(String modId) {
        return new RegistrarImpl(modId);
    }
}
