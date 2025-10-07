package com.cafepos.demo;

import com.cafepos.common.Money;
import com.cafepos.domain.*;
import com.cafepos.Payment.CashPayment;

public final class Week4Demo {
    public static void main(String[] args) {
        // Create a simple product and order
        var espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        var order = new Order(1005);

        // Register the three observers
        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());

        // Add 1x Espresso to the order
        order.addItem(new LineItem(espresso, 1));

        // Pay using CashPayment strategy
        order.pay(new CashPayment());

        // Mark the order as ready
        order.markReady();
    }
}
