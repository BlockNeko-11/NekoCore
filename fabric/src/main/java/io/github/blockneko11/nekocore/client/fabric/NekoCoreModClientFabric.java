package io.github.blockneko11.nekocore.client.fabric;

import io.github.blockneko11.nekocore.client.NekoCoreModClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NekoCoreModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NekoCoreModClient.initClient();
    }
}
