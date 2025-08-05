package io.github.blockneko11.nekoplatform.client.fabric;

import io.github.blockneko11.nekoplatform.client.NekoPlatformModClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NekoPlatformModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NekoPlatformModClient.initClient();
    }
}
