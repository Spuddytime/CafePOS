package com.cafepos.demo;

import com.cafepos.infra.Wiring;
import com.cafepos.ui.ConsoleView;
import com.cafepos.ui.OrderController;

public final class Week10Demo_MVC {
    public static void main(String[] args) {
        // create wired components (repo, pricing, formatter, checkout, ...)
        var components = Wiring.createDefault();

        // build controller + view
        var controller = new OrderController(components.repo(), components.checkout());
        var view = new ConsoleView();

        // create an order and add items (matches lab example)
        long id = 4101L;
        controller.createOrder(id);
        controller.addItem(id, "ESP+SHOT+OAT", 1);
        controller.addItem(id, "LAT+L", 2);

        // perform checkout (returns receipt string) — controller delegates to app layer
        String receipt = controller.checkout(id);

        // view prints the receipt (no formatting done here)
        view.print(receipt);

        // extra blank line for neatness
        view.println();
        view.println("[Demo] Week10 MVC finished.");
    }
}
