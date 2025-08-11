package io.github.blockneko11.nekocore.data.provider;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class NekoTranslationProvider implements DataProvider {
    private final String modId;
    private final DataOutput output;
    private final String language;

    public NekoTranslationProvider(String modId, boolean validate, DataOutput output, String language) {
        this.modId = modId;
        this.output = output;
        this.language = language;
    }

    public abstract void generateTranslations(TranslationBuilder builder);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Map<String, String> translations = new TreeMap<>();

        this.generateTranslations((k, v) -> {
            if (translations.containsKey(k)) {
                throw new IllegalStateException("Duplicate translation key: " + k);
            }

            translations.put(k, v);
        });

        JsonObject json = new JsonObject();
        translations.forEach(json::addProperty);
        return DataProvider.writeToPath(writer,
                json,
                this.output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "lang")
                        .resolveJson(new Identifier(this.modId, this.language)));
    }

    @Override
    public final String getName() {
        return "Translation Provider (NekoCore)";
    }

    public static abstract class EN_US extends NekoTranslationProvider {
        public EN_US(String modId, boolean validate, DataOutput output) {
            super(modId, validate, output, "en_us");
        }
    }

    public static abstract class ZH_CN extends NekoTranslationProvider {
        public ZH_CN(String modId, boolean validate, DataOutput output) {
            super(modId, validate, output, "zh_cn");
        }
    }

    @FunctionalInterface
    public interface TranslationBuilder {
        void add(String key, String value);

        default void add(Item item, String value) {
            this.add(item.getTranslationKey(), value);
        }

        default void add(Block block, String value) {
            this.add(block.getTranslationKey(), value);
        }

        default void add(Identifier identifier, String value) {
            add(identifier.toTranslationKey(), value);
        }
    }
}
