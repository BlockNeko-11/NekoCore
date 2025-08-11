package io.github.blockneko11.nekocore.data.fabric;

import io.github.blockneko11.nekocore.data.NekoDataGeneration;
import io.github.blockneko11.nekocore.mixin.fabric.access.FabricDataGeneratorAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NekoDataGenerationFabric extends NekoDataGeneration {
    public NekoDataGenerationFabric(FabricDataGenerator generator) {
        super(generator.getModId(),
                ((FabricDataGeneratorAccessor) (Object) generator).isStrictValidation(),
                generator.createPack(),
                ((FabricDataGeneratorAccessor) (Object) generator).getRegistriesFuture());
    }
}
