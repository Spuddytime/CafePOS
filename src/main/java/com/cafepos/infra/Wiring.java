package com.cafepos.infra;

import com.cafepos.checkout.CheckoutService;
import com.cafepos.factory.ProductFactory;
import com.cafepos.pricing.FixedRateTaxPolicy;
import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.app.ReceiptFormatter;
import com.cafepos.domain.OrderRepository;

public final class Wiring {

    //Holder for common components
    public static record Components(
            OrderRepository repo,
            PricingService pricing,
            ReceiptPrinter quickPrinter,
            ReceiptFormatter formatter,
            CheckoutService checkout
    )
    {}

    /**
     * Full wiring: in-memory repo, pricing pipeline, both printers/formatters,
     * and a CheckoutService that supports order-based checkout (Week 10 specific spec).
     */
    public static Components createDefault() {
        // Infra / repo
        OrderRepository repo = new InMemoryOrderRepository();

        // Pricing: Loyalty 5% + fixed 10% tax (matches lab examples)
        var pricing = new PricingService(
                new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10)
        );

        // Quick recipe printer (keeps Week-5/6 formatting)
        var quickPrinter = new ReceiptPrinter();

        // App-layer receipt formatter (formats full Order with line items)
        var formatter = new ReceiptFormatter();

        // Checkout service wired with both quickPrinter and order formatter + repo
        var checkout = new CheckoutService(
                new ProductFactory(),
                pricing,
                quickPrinter,
                formatter,
                repo,
                10 // taxPercent
        );

        return new Components(repo, pricing, quickPrinter, formatter, checkout);
    }

    /**
     * Minimal wiring for earlier lab work
     * (keeps legacy demos working).
     */
    public static CheckoutService createRecipeOnly() {
        var pricing = new PricingService(
                new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10)
        );
        return new CheckoutService(new ProductFactory(), pricing, new ReceiptPrinter(), 10);
    }
}
