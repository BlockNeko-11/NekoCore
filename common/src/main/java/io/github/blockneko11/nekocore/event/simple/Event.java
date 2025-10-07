package io.github.blockneko11.nekocore.event.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Event<T> {
    private final ArrayList<T> listeners = new ArrayList<>();
    private final Function<List<T>, T> function;

    protected Event(Function<List<T>, T> function) {
        this.function = function;
    }

    public void register(T listener) {
        this.listeners.add(listener);
    }

    public void unregister(T listener) {
        this.listeners.remove(listener);
        this.listeners.trimToSize();
    }

    public boolean isRegistered(T listener) {
        return this.listeners.contains(listener);
    }

    public void clear() {
        this.listeners.clear();
        this.listeners.trimToSize();
    }

    public T invoker() {
        if (this.listeners.size() == 1) {
            return this.listeners.get(0);
        }

        return this.function.apply(this.listeners);
    }
}
