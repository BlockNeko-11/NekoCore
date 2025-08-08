package io.github.blockneko11.nekocore.forge;

// import dev.architectury.platform.forge.EventBuses;
import io.github.blockneko11.nekocore.NekoCoreMod;
import io.github.blockneko11.nekocore.client.NekoCoreModClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(NekoCoreMod.MOD_ID)
public class NekoCoreModForge {
    public NekoCoreModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//        EventBuses.registerModEventBus(NekoCoreMod.MOD_ID, bus);
        NekoCoreMod.init();

        if (FMLEnvironment.dist.isClient()) {
            NekoCoreModClient.initClient();
        }
    }
}
