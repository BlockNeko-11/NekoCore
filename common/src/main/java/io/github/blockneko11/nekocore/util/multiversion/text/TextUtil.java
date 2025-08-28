package io.github.blockneko11.nekocore.util.multiversion.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class TextUtil {
    public static MutableText literal(String text) {
        return Text.literal(text);
    }

    public static MutableText translatable(String key) {
        return Text.translatable(key);
    }

    public static Text copy(Text text) {
        return literal(text.getString()).setStyle(text.getStyle());
    }

    private TextUtil() {
    }
}
