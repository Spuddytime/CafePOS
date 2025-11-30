package com.cafepos.ui;

import com.cafepos.app.events.EventBus;
import com.cafepos.app.events.OrderCreated;
import com.cafepos.app.events.OrderPaid;
import com.cafepos.infra.Wiring;

public final class EventWiringDemo {

    public static void main(String[] args) {
        // 1) Create the event bus (connector)
        var bus = new EventBus();

        // 2) Get our wired components (repo + checkout) from the composition root
        var components = Wiring.createDefault();

        // 3) Create the controller (component that talks to the domain)
        var controller = new OrderController(components.repo(), components.checkout());

        // 4) Subscribe UI handlers to events
        bus.on(OrderCreated.class,
                e -> System.out.println("[UI] order created: " + e.orderId()));
        bus.on(OrderPaid.class,
                e -> System.out.println("[UI] order paid: " + e.orderId()));

        // 5) Simulate flow
        long id = 4201L;

        // Controller creates an order in the domain
        controller.createOrder(id);

        // UI layer reacts to domain events via the bus
        bus.emit(new OrderCreated(id));

        // later, after payment in a real system:
        bus.emit(new OrderPaid(id));

        System.out.println("[Demo] Event wiring demo finished.");
    }
}
