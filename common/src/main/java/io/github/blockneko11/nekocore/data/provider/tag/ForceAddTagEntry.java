package io.github.blockneko11.nekocore.data.provider.tag;

import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ForceAddTagEntry extends TagEntry {
    private final TagEntry delegate;

    public ForceAddTagEntry(TagEntry delegate) {
        super(delegate.id, true, delegate.required);
        this.delegate = delegate;
    }

    @Override
    public <T> boolean resolve(TagEntry.ValueGetter<T> arg, Consumer<T> consumer) {
        return this.delegate.resolve(arg, consumer);
    }

    @Override
    public boolean canAdd(Predicate<Identifier> objectExistsTest, Predicate<Identifier> tagExistsTest) {
        return true;
    }
}
