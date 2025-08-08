package io.github.blockneko11.nekocore.event.bus;

import io.github.blockneko11.nekocore.event.bus.api.Event;

import java.lang.reflect.Method;
import java.util.function.Consumer;

abstract class EventListener<T extends Event> {
    private final int priority;

    EventListener(int priority) {
        this.priority = priority;
    }

    int getPriority() {
        return priority;
    }

    protected abstract void handle(T event);

    static final class MethodListener<T extends Event> extends EventListener<T> {
        private final Method method;

        MethodListener(Method method, int priority) {
            super(priority);
            this.method = method;
        }

        @Override
        protected void handle(T event) {
            try {
                this.method.invoke(null, event);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to handle event", e);
            }
        }
    }

    static final class ConsumerListener<T extends Event> extends EventListener<T> {
        private final Consumer<T> consumer;

        ConsumerListener(Consumer<T> consumer, int priority) {
            super(priority);
            this.consumer = consumer;
        }

        @Override
        protected void handle(T event) {
            this.consumer.accept(event);
        }
    }
}
