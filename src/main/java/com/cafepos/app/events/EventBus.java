package com.cafepos.app.events;

import java.util.*;
import java.util.function.Consumer;

/**
 * Very small in-process EventBus for typed events. Handlers are stored by event class.
 */
public final class EventBus {
    private final Map<Class<?>, List<Consumer<?>>> handlers = new HashMap<>();

    /**
     * Register a handler for events of type T.
     */
    public <T> void on(Class<T> type, Consumer<T> handler) {
        handlers.computeIfAbsent(type, k -> new ArrayList<>()).add(handler);
    }

    /**
     * Emit an event ---- all handlers registered for this event's exact runtime class are invoked.
     */
    @SuppressWarnings("unchecked")
    public <T> void emit(T event) {
        var list = handlers.getOrDefault(event.getClass(), List.of());
        for (var h : list) ((Consumer<T>) h).accept(event);
    }
}
