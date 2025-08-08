package io.github.blockneko11.nekocore.event.bus;

import io.github.blockneko11.nekocore.event.bus.api.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EventListenerList<T extends Event> {
    private final List<EventListener<T>> delegate = Collections.synchronizedList(new ArrayList<>());

    void add(EventListener<T> listener) {
        this.delegate.add(listener);
    }

    void remove(EventListener<T> listener) {
        this.delegate.remove(listener);
    }

    void post(T event) {
        this.delegate.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
        this.delegate.forEach(listener -> listener.handle(event));
    }
}
