package net.examplemod.data.fabric;

// import io.github.erha134.mc.sparklib.data.fabric.SDataGenerationFabric;
// import net.examplemod.ExampleMod;
// import net.examplemod.data.ExampleModDataGeneration;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class ExampleModDataGenerationFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        // ExampleModDataGeneration.initDataGen(SDataGenerationFabric.create(ExampleMod.MOD_ID, fabricDataGenerator));
    }
}
