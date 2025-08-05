package io.github.blockneko11.nekoplatform.fabric;

import io.github.blockneko11.nekoplatform.NekoPlatformMod;
import net.fabricmc.api.ModInitializer;

public class NekoPlatformModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NekoPlatformMod.init();
    }
}
