package io.github.blockneko11.nekoplatform.forge;

// import dev.architectury.platform.forge.EventBuses;
import io.github.blockneko11.nekoplatform.NekoPlatformMod;
import io.github.blockneko11.nekoplatform.client.NekoPlatformModClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class NekoPlatformModForge {
    public NekoPlatformModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//        EventBuses.registerModEventBus(NekoPlatformMod.MOD_ID, bus);
        NekoPlatformMod.init();

        if (FMLEnvironment.dist.isClient()) {
            NekoPlatformModClient.initClient();
        }
    }
}
