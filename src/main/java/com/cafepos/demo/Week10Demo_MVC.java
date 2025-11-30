package com.cafepos.demo;

import com.cafepos.infra.Wiring;
import com.cafepos.ui.ConsoleView;
import com.cafepos.ui.OrderController;

public final class Week10Demo_MVC {
    public static void main(String[] args) {

        // --- Create wired components (Composition Root) ---
        var components = Wiring.createDefault();

        // controller → application layer
        var controller = new OrderController(components.repo(), components.checkout());

        // view → presentation layer
        var view = new ConsoleView();

        // --- Create an order and add items ---
        long id = 4101L;
        controller.createOrder(id);

        controller.addItem(id, "ESP+SHOT+OAT", 1);
        controller.addItem(id, "LAT+L", 2);

        // --- Checkout returns receipt text (controller → app-layer service) ---
        String receipt = controller.checkout(id); // uses default tax (10%)

        // --- View prints the receipt ---
        view.print(receipt);

        view.println();
        view.println("[Demo] Week10 MVC finished.");
    }
}
