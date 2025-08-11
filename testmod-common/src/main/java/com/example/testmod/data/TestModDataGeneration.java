package com.example.testmod.data;

import io.github.blockneko11.nekocore.data.NekoDataGeneration;

public final class TestModDataGeneration {
    public static void initDataGen(NekoDataGeneration gen) {
        gen.addProvider(TestItemTagProvider::new);
        gen.run();
    }
}
