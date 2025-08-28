package io.github.blockneko11.nekocore.util.multiversion.util;

import net.minecraft.util.Identifier;

public final class IdentifierUtil {
    public static Identifier create(String modId, String path) {
        return new Identifier(modId, path);
    }

    private IdentifierUtil() {
    }
}
