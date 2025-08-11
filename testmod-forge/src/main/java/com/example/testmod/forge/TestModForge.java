package com.example.testmod.forge;

import com.example.testmod.TestMod;
import com.example.testmod.data.TestModDataGeneration;
import dev.architectury.platform.forge.EventBuses;
import io.github.blockneko11.nekocore.data.forge.NekoDataGenerationForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestMod.MOD_ID)
public final class TestModForge {
    public TestModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(TestMod.MOD_ID, bus);

        bus.addListener(this::onGatherData);
        TestMod.init();
    }

    private void onGatherData(GatherDataEvent e) {
        TestModDataGeneration.initDataGen(new NekoDataGenerationForge(e));
    }
}
