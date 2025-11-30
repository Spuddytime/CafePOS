// src/main/java/com/cafepos/infra/Wiring.java
package com.cafepos.infra;

import com.cafepos.app.CheckoutService;
import com.cafepos.app.ReceiptFormatter;
import com.cafepos.domain.OrderRepository;
import com.cafepos.pricing.*;
import com.cafepos.domain.*;  // for OrderRepository, etc.

public final class Wiring {

    public static record Components(
            OrderRepository repo,
            PricingService pricing,
            CheckoutService checkout
    ) {}

    public static Components createDefault() {
        OrderRepository repo = new InMemoryOrderRepository();

        PricingService pricing = new PricingService(
                new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10)
        );

        ReceiptFormatter formatter = new ReceiptFormatter();

        CheckoutService checkout = new CheckoutService(repo, pricing, formatter);

        return new Components(repo, pricing, checkout);
    }
}
