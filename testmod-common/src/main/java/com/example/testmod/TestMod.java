package com.example.testmod;

import com.example.testmod.registry.TestRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestMod {
    public static final String MOD_ID = "testmod";
    public static final String MOD_NAME = "Test Mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Hello from Test Mod!");
        TestRegistry.init();
    }
}
