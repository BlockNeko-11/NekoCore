package io.github.blockneko11.nekocore.data.provider;

import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class NekoModelProvider extends ModelProvider {
    private final String modId;
    private final boolean validate;

    public NekoModelProvider(String modId, boolean validate, DataOutput output) {
        super(output);
        this.modId = modId;
        this.validate = validate;
    }

    public abstract void generateBlockStateModels(BlockStateModelGenerator generator);

    public abstract void generateItemModels(ItemModelGenerator generator);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Map<Block, BlockStateSupplier> blockStates = new HashMap<>();
        Consumer<BlockStateSupplier> blockCollector = (supplier -> {
            Block block = supplier.getBlock();
            if (blockStates.containsKey(block)) {
                throw new IllegalStateException("Duplicate block state definition for " + block);
            }

            blockStates.put(block, supplier);
        });

        Map<Identifier, Supplier<JsonElement>> models = new HashMap<>();
        BiConsumer<Identifier, Supplier<JsonElement>> modelCollector = ((id, supplier) -> {
            if (models.containsKey(id)) {
                throw new IllegalStateException("Duplicate model definition for " + id);
            }

            models.put(id, supplier);
        });

        Set<Item> items = new HashSet<>();
        BlockStateModelGenerator blockStateModelGenerator = new BlockStateModelGenerator(blockCollector, modelCollector, items::add);
        this.generateBlockStateModels(blockStateModelGenerator);
        blockStateModelGenerator.register();

        ItemModelGenerator itemModelGenerator = new ItemModelGenerator(modelCollector);
        this.generateItemModels(itemModelGenerator);
        itemModelGenerator.register();

        // check models if missing block states
        List<Block> blocksMissingState = Registries.BLOCK.stream().filter(block -> {
            if (!this.validate) {
                return false;
            }

            if (!Registries.BLOCK.getId(block).getNamespace().equals(this.modId)) {
                return false;
            }

            return !blockStates.containsKey(block);
        }).toList();
        if (!blocksMissingState.isEmpty()) {
            throw new IllegalStateException("Missing block state definition for " + blocksMissingState);
        }

        Registries.BLOCK.forEach(b -> {
            Item item = Item.BLOCK_ITEMS.get(b);
            if (item == null || items.contains(item)) {
                return;
            }

            if (blockStates.containsKey(b)) {
                return;
            }

            if (!Registries.ITEM.getId(item).getNamespace().equals(this.modId)) {
                return;
            }

            Identifier id = ModelIds.getItemModelId(item);
            if (models.containsKey(id)) {
                return;
            }

            models.put(id, new SimpleModelSupplier(ModelIds.getBlockModelId(b)));
        });

        CompletableFuture<?>[] blockStatesFutures = blockStates.entrySet().stream().map(e -> {
            Block block = e.getKey();
            Path path = this.blockstatesPathResolver.resolveJson(block.getRegistryEntry().registryKey().getValue());
            JsonElement json = e.getValue().get();
            return DataProvider.writeToPath(writer, json, path);
        }).toArray(i -> new CompletableFuture<?>[i]);

        CompletableFuture<?>[] modelFutures = models.entrySet().stream().map(e -> {
            Identifier id = e.getKey();
            Path path = this.modelsPathResolver.resolveJson(id);
            JsonElement json = e.getValue().get();
            return DataProvider.writeToPath(writer, json, path);
        }).toArray(i -> new CompletableFuture<?>[i]);

        return CompletableFuture.allOf(CompletableFuture.allOf(blockStatesFutures), CompletableFuture.allOf(modelFutures));
    }
}
