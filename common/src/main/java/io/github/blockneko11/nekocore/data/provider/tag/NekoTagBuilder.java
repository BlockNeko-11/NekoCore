package io.github.blockneko11.nekocore.data.provider.tag;

import lombok.Getter;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class NekoTagBuilder<T> extends TagBuilder {
    private final Function<T, RegistryKey<T>> toRegKeyFunction;

    @Getter
    private boolean replace = false;

    public NekoTagBuilder(Function<T, RegistryKey<T>> toRegKeyFunction) {
        super();
        this.toRegKeyFunction = toRegKeyFunction;
    }

    public NekoTagBuilder<T> replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    // add

    public NekoTagBuilder<T> add(T entry) {
        return this.add(this.toRegKeyFunction.apply(entry));
    }

    public NekoTagBuilder<T> add(Supplier<T> supplier) {
        return this.add(supplier.get());
    }

    @SafeVarargs
    public final NekoTagBuilder<T> add(T... entries) {
        Arrays.stream(entries)
                .map(this.toRegKeyFunction)
                .forEach(this::add);

        return this;
    }

    public NekoTagBuilder<T> add(RegistryKey<T> key) {
        return this.add(key.getValue());
    }

    @SafeVarargs
    public final NekoTagBuilder<T> add(RegistryKey<T>... keys) {
        for (RegistryKey<T> registryKey : keys) {
            this.add(registryKey);
        }

        return this;
    }

    @Override
    public NekoTagBuilder<T> add(Identifier id) {
        super.add(id);
        return this;
    }

    public NekoTagBuilder<T> add(Identifier... ids) {
        for (Identifier id : ids) {
            this.add(id);
        }

        return this;
    }

    @Override
    public NekoTagBuilder<T> add(TagEntry entry) {
        super.add(entry);
        return this;
    }

    // add optional

    @Override
    public NekoTagBuilder<T> addOptional(Identifier id) {
        super.addOptional(id);
        return this;
    }

    public NekoTagBuilder<T> addOptional(RegistryKey<T> key) {
        return this.addOptional(key.getValue());
    }

    // add tag


    @Override
    public NekoTagBuilder<T> addTag(Identifier id) {
        super.addTag(id);
        return this;
    }

    public NekoTagBuilder<T> addTag(TagKey<T> tag) {
        return this.addTag(tag.id());
    }

    @Override
    public NekoTagBuilder<T> addOptionalTag(Identifier id) {
        super.addOptionalTag(id);
        return this;
    }

    public NekoTagBuilder<T> addOptionalTag(TagKey<T> tag) {
        return this.addOptionalTag(tag.id());
    }

    public NekoTagBuilder<T> forceAddTag(TagKey<T> tag) {
        return this.add(new ForceAddTagEntry(TagEntry.create(tag.id())));
    }
}
