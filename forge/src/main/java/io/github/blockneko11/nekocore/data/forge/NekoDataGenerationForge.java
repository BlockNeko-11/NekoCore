package io.github.blockneko11.nekocore.data.forge;

import io.github.blockneko11.nekocore.data.NekoDataGeneration;
import net.minecraftforge.data.event.GatherDataEvent;

public class NekoDataGenerationForge extends NekoDataGeneration {
    public NekoDataGenerationForge(GatherDataEvent e) {
        super(e.getModContainer().getModId(),
                e.validate(),
                e.getGenerator().createVanillaSubPack(true, e.getModContainer().getModId()),
                e.getLookupProvider());
    }
}
