package io.github.blockneko11.nekocore.fabric;

import io.github.blockneko11.nekocore.NekoCoreMod;
import net.fabricmc.api.ModInitializer;

public class NekoCoreModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NekoCoreMod.init();
    }
}
