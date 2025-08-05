package net.examplemod.forge;

import dev.architectury.platform.forge.EventBuses;
// import io.github.erha134.mc.sparklib.data.forge.SDataGenerationForge;
import net.examplemod.ExampleMod;
import net.examplemod.client.ExampleModClient;
// import net.examplemod.data.ExampleModDataGeneration;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(ExampleMod.MOD_ID, bus);
        ExampleMod.init();

        if (FMLEnvironment.dist.isClient()) {
            ExampleModClient.initClient();
        }

        bus.addListener(this::onDataGen);
    }

    private void onDataGen(GatherDataEvent e) {
        // ExampleModDataGeneration.initDataGen(SDataGenerationForge.create(ExampleMod.MOD_ID, e));
    }
}
