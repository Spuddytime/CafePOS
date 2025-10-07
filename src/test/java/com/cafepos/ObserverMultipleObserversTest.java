package com.cafepos;

import com.cafepos.domain.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverMultipleObserversTest {

    @Test
    void multiple_observers_receive_ready_event() {
        var o = new Order(42);

        List<String> observerAEvents = new ArrayList<>();
        List<String> observerBEvents = new ArrayList<>();

        // Register two fake observers that just record events
        o.register((order, evt) -> observerAEvents.add(evt));
        o.register((order, evt) -> observerBEvents.add(evt));

        // Trigger an event
        o.markReady();

        // Assert both observers received the same event
        assertTrue(observerAEvents.contains("ready"));
        assertTrue(observerBEvents.contains("ready"));
    }
}
