package io.github.blockneko11.nekocore.data.provider.tag;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.github.blockneko11.nekocore.NekoCoreMod;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagFile;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class NekoTagProvider<T> implements DataProvider {
    private final DataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
    private final RegistryKey<? extends Registry<T>> registryRef;
    private final Map<Identifier, NekoTagBuilder<T>> tagBuilders = new LinkedHashMap<>();

    public NekoTagProvider(String modId,
                           boolean validate,
                           DataOutput output,
                           CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture,
                           RegistryKey<? extends Registry<T>> registryRef) {
        this.output = output;
        this.registriesFuture = registriesFuture;
        this.registryRef = registryRef;
    }

    public RegistryKey<T> toRegistryKey(T entry) {
        // to make javac happy
        Registry<T> registry = (Registry<T>) Registries.REGISTRIES.get((RegistryKey) this.registryRef);

        if (registry != null) {
            Optional<RegistryKey<T>> key = registry.getKey(entry);

            if (key.isPresent()) {
                return key.get();
            }
        }

        throw new UnsupportedOperationException("Adding objects is not supported by " + this.getClass().getCanonicalName());
    }

    public abstract void configure(RegistryWrapper.WrapperLookup lookup);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return this.registriesFuture
                .thenApply(lookup -> {
                    this.tagBuilders.clear();
                    this.configure(lookup);
                    return lookup;
                })
                .thenCompose(lookup -> {
                    RegistryWrapper.Impl<T> wrapper = lookup.getWrapperOrThrow(this.registryRef);
                    Predicate<Identifier> objectExists = (id -> wrapper.getOptional(RegistryKey.of(this.registryRef, id)).isPresent());
                    return CompletableFuture.allOf(this.tagBuilders.entrySet()
                            .stream()
                            .map(entry -> {
                                Identifier id = entry.getKey();
                                NekoTagBuilder<T> builder = entry.getValue();
                                List<TagEntry> entries = new ArrayList<>(builder.build()); // List#copyOf() returns an immutable list
                                entries.removeIf(tagEntry -> !tagEntry.canAdd(objectExists, this.tagBuilders::containsKey));

                                if (entries.isEmpty()) {
                                    throw new IllegalArgumentException(String.format(Locale.ROOT, "Couldn't define tag %s as it is missing following references: %s", id, entries.stream().map(Objects::toString).collect(Collectors.joining(","))));
                                }

                                JsonElement json = TagFile.CODEC.encodeStart(JsonOps.INSTANCE, new TagFile(entries, builder.isReplace()))
                                        .getOrThrow(false, NekoCoreMod.LOGGER::error);

                                return DataProvider.writeToPath(writer,
                                        json,
                                        this.output.getResolver(DataOutput.OutputType.DATA_PACK, TagManagerLoader.getPath(registryRef))
                                                .resolveJson(id));
                            })
                            .toArray(CompletableFuture<?>[]::new));
                });
    }

    protected NekoTagBuilder<T> getTagBuilder(TagKey<T> tag) {
        return this.tagBuilders.computeIfAbsent(tag.id(), $ -> new NekoTagBuilder<>(this::toRegistryKey));
    }

    @Override
    public String getName() {
        return "Tag Provider (Neko Core)";
    }

    public static abstract class BlockTagProvider extends NekoTagProvider<Block> {
        public BlockTagProvider(String modId,
                                boolean validate,
                                DataOutput output,
                                CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(modId, validate, output, registriesFuture, RegistryKeys.BLOCK);
        }
    }

    public static abstract class ItemTagProvider extends NekoTagProvider<Item> {
        @Nullable
        private final NekoTagProvider.BlockTagProvider blockTagProvider;

        public ItemTagProvider(String modId,
                               boolean validate,
                               DataOutput output,
                               CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            this(modId, validate, output, registriesFuture, null);
        }

        public ItemTagProvider(String modId,
                               boolean validate,
                               DataOutput output,
                               CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture,
                               @Nullable NekoTagProvider.BlockTagProvider blockTagProvider) {
            super(modId, validate, output, registriesFuture, RegistryKeys.ITEM);
            this.blockTagProvider = blockTagProvider;
        }

        @Override
        public RegistryKey<Item> toRegistryKey(Item entry) {
            return entry.getRegistryEntry().registryKey();
        }

        public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
            if (this.blockTagProvider == null) {
                throw new IllegalStateException("Cannot copy block tags to item tags without a block tag provider");
            }
            NekoTagBuilder<Item> itemBuilder = this.getTagBuilder(itemTag);
            this.blockTagProvider.getTagBuilder(blockTag)
                    .build()
                    .forEach(itemBuilder::add);
        }
    }
}
