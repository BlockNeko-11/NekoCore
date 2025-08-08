package com.example.testmod.event;

import com.example.testmod.TestMod;
import io.github.blockneko11.nekocore.event.bus.EventSubscriber;

public class TestEventListener {
    @EventSubscriber
    public static void onTest(TestEvent e) {
        TestMod.LOGGER.info("Test event triggered! #1");
    }
}
