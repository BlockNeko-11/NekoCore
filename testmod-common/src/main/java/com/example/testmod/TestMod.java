package com.example.testmod;

import com.example.testmod.event.TestEvent;
import com.example.testmod.event.TestEventListener;
import com.example.testmod.registry.TestRegistry;
import io.github.blockneko11.nekoplatform.event.bus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestMod {
    public static final String MOD_ID = "testmod";
    public static final String MOD_NAME = "Test Mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final EventBus BUS = new EventBus();

    public static void init() {
        LOGGER.info("Hello from Test Mod!");
        TestRegistry.init();
        BUS.registerClass(new TestEventListener());
        BUS.registerListener(TestMod::onTestEventTriggered);
    }

    private static void onTestEventTriggered(TestEvent e) {
        LOGGER.info("Test event triggered! #2");
    }
}
