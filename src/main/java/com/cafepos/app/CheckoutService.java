// src/main/java/com/cafepos/app/CheckoutService.java
package com.cafepos.app;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderRepository;
import com.cafepos.pricing.PricingService;

public final class CheckoutService {

    private final OrderRepository orders;
    private final PricingService pricing;
    private final ReceiptFormatter formatter;

    public CheckoutService(OrderRepository orders,
                           PricingService pricing,
                           ReceiptFormatter formatter) {
        this.orders = orders;
        this.pricing = pricing;
        this.formatter = formatter;
    }

    /** Application-level checkout: look up order, price it, format receipt. */
    public String checkout(long orderId, int taxPercent) {
        Order order = orders.findById(orderId).orElseThrow();
        var pr = pricing.price(order.subtotal());
        return formatter.format(orderId, order.items(), pr, taxPercent);
    }
}
