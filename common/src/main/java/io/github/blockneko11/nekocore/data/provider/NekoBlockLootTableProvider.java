package io.github.blockneko11.nekocore.data.provider;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class NekoBlockLootTableProvider extends BlockLootTableGenerator implements DataProvider {
    private final String modId;
    private final boolean validate;
    private final DataOutput output;

    private final Set<Identifier> validateExcludes = new HashSet<>();

    public NekoBlockLootTableProvider(String modId, boolean validate, DataOutput output) {
        super(Collections.emptySet(), FeatureFlags.FEATURE_MANAGER.getFeatureSet());
        this.modId = modId;
        this.validate = validate;
        this.output = output;
    }

    public void excludeValidate(Block block) {
        this.validateExcludes.add(Registries.BLOCK.getId(block));
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
        this.generate();
        for (Map.Entry<Identifier, LootTable.Builder> entry : this.lootTables.entrySet()) {
            Identifier id = entry.getKey();

            if (id.equals(LootTables.EMPTY)) {
                continue;
            }

            exporter.accept(id, entry.getValue());
        }

        if (this.validate) {
            Set<Identifier> missing = new HashSet<>();

            for (Identifier blockId : Registries.BLOCK.getIds()) {
                if (blockId.getNamespace().equals(this.modId)) {
                    Identifier blockLootTableId = Registries.BLOCK.get(blockId).getLootTableId();

                    if (blockLootTableId.getNamespace().equals(this.modId)) {
                        if (!this.lootTables.containsKey(blockLootTableId)) {
                            missing.add(blockId);
                        }
                    }
                }
            }

            missing.removeAll(this.validateExcludes);

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing block loot table for " + missing);
            }
        }
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Map<Identifier, LootTable> builders = new HashMap<>();
        this.accept((id, builder) -> {
            if (builders.containsKey(id)) {
                throw new IllegalStateException("Duplicate loot table definition for " + id);
            }

            builders.put(id, builder.type(LootContextTypes.BLOCK).build());
        });

        CompletableFuture<?>[] futures = builders.entrySet()
                .stream()
                .map(e -> {
                    JsonObject json = (JsonObject) LootDataType.LOOT_TABLES.getGson().toJsonTree(e.getValue());
                    return DataProvider.writeToPath(writer,
                            json,
                            this.output.getResolver(DataOutput.OutputType.DATA_PACK, "loot_tables")
                                    .resolveJson(e.getKey()));
                }).toArray(CompletableFuture<?>[]::new);

        return CompletableFuture.allOf(futures);
    }

    @Override
    public final String getName() {
        return "Block Loot Table Provider (Neko Core)";
    }
}
