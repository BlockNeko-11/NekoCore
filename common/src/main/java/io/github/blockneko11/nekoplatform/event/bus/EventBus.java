package io.github.blockneko11.nekoplatform.event.bus;

import io.github.blockneko11.nekoplatform.event.bus.api.Event;
import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {
    private final Map<Class<? extends Event>, EventListenerList<?>> listeners = new ConcurrentHashMap<>();

    public void registerClass(Object o) {
        Class<?> type = o.getClass();

        for (Method m : type.getDeclaredMethods()) {
            int mod = m.getModifiers();
            if (!Modifier.isStatic(mod)) {
                throw new IllegalArgumentException("Event listener should be static");
            }

            if (!m.isAnnotationPresent(EventSubscriber.class)) {
                continue;
            }

            this.registerMethod(m);
        }
    }

    private void registerMethod(Method m) {
        Class<?>[] paramTypes = m.getParameterTypes();

        if (paramTypes.length != 1) {
            throw new IllegalArgumentException("Event listener should have only one parameter");
        }

        Class<?> eventType = paramTypes[0];

        if (!Event.class.isAssignableFrom(eventType)) {
            throw new IllegalArgumentException("The first parameter of event listener should be an event");
        }

        EventSubscriber annotation = m.getAnnotation(EventSubscriber.class);
        this.register((Class<? extends Event>) eventType, new EventListener.MethodListener<>(m, annotation.priority()));
    }

    public <T extends Event> void registerListener(Consumer<T> listener) {
        this.registerListener(listener, 0);
    }

    public <T extends Event> void registerListener(Consumer<T> listener, int priority) {
        this.registerListener(this.getEventType(listener), listener, priority);
    }

    private <T extends Event> Class<T> getEventType(Consumer<T> listener) {
        Class<?> eventType = TypeResolver.resolveRawArgument(Consumer.class, listener.getClass());
        if (eventType == TypeResolver.Unknown.class) {
            throw new IllegalArgumentException("Cannot resolve event type");
        }

        return (Class<T>) eventType;
    }

    public <T extends Event> void registerListener(Class<T> eventType, Consumer<T> listener) {
        this.registerListener(eventType, listener, 0);
    }

    public <T extends Event> void registerListener(Class<T> eventType, Consumer<T> listener, int priority) {
        this.register(eventType, new EventListener.ConsumerListener<>(listener, priority));
    }

    private <T extends Event> void register(Class<T> eventType, EventListener<T> listener) {
        EventListenerList<T> list = (EventListenerList<T>) this.listeners.computeIfAbsent(
                eventType,
                $ -> new EventListenerList<>());

        list.add(listener);
    }

    public <T extends Event> void post(T event) {
        EventListenerList<T> list = (EventListenerList<T>) this.listeners.get(event.getClass());
        if (list == null) {
            return;
        }

        list.post(event);
    }
}
