package com.example.testmod.data.fabric;

import com.example.testmod.data.TestModDataGeneration;
import io.github.blockneko11.nekocore.data.fabric.NekoDataGenerationFabric;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class TestModDataGenerationFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        TestModDataGeneration.initDataGen(new NekoDataGenerationFabric(fabricDataGenerator));
    }
}
