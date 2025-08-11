package io.github.blockneko11.nekocore.mixin.fabric.access;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.CompletableFuture;

@Mixin(value = FabricDataGenerator.class, remap = false)
public interface FabricDataGeneratorAccessor {
    @Accessor("strictValidation")
    boolean isStrictValidation();

    @Accessor("registriesFuture")
    CompletableFuture<RegistryWrapper.WrapperLookup> getRegistriesFuture();
}
