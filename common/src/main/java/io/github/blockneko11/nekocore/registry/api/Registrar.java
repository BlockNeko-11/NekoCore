package io.github.blockneko11.nekocore.registry.api;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.blockneko11.nekocore.util.multiversion.util.IdentifierUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public abstract class Registrar {
    protected final String modId;
    private boolean registered = false;

    protected Registrar(String modId) {
        this.modId = modId;
    }

    public abstract <R, T extends R> Supplier<T> register(RegistryKey<? extends Registry<T>> registry,
                                                          String id,
                                                          T entry);

    protected Identifier createId(String id) {
        return IdentifierUtil.create(this.modId, id);
    }

    public void register() {
        if (this.registered) {
            throw new IllegalStateException("Cannot register twice");
        }

        this.registered = true;
        this.onRegister();
    }

    protected abstract void onRegister();

    @ExpectPlatform
    public static Registrar create(String modId) {
        throw new AssertionError();
    }
}
