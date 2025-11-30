package com.cafepos.checkout;

import com.cafepos.common.Money;
import com.cafepos.common.Priced;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderRepository;
import com.cafepos.factory.ProductFactory;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.app.ReceiptFormatter; // app-layer formatter for Order/LineItem lists

import java.util.List;
import java.util.Objects;

public final class CheckoutService {

    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter quickReceiptPrinter; // used by recipe-based convenience method
    private final ReceiptFormatter orderFormatter;    // formats full Order receipts (app-layer)
    private final OrderRepository orders;
    private final int taxPercent;


    public CheckoutService(ProductFactory factory,
                           PricingService pricing,
                           ReceiptPrinter quickReceiptPrinter,
                           int taxPercent) {
        this(factory, pricing, quickReceiptPrinter, null, null, taxPercent);
    }

  // This is needed for week 10 wiring
    public CheckoutService(ProductFactory factory,
                           PricingService pricing,
                           ReceiptPrinter quickReceiptPrinter,
                           ReceiptFormatter orderFormatter,
                           OrderRepository orders,
                           int taxPercent) {
        this.factory = Objects.requireNonNull(factory);
        this.pricing = Objects.requireNonNull(pricing);
        this.quickReceiptPrinter = Objects.requireNonNull(quickReceiptPrinter);
        this.orderFormatter = orderFormatter; // may be null for minimal setups
        this.orders = orders;                 // may be null for minimal setups
        if (taxPercent < 0) throw new IllegalArgumentException("taxPercent required");
        this.taxPercent = taxPercent;
    }


    public String checkout(String recipe, int qty) {

        if (recipe == null || recipe.isBlank()) throw new IllegalArgumentException("recipe required");
        if (qty <= 0) qty = 1;

        var product = factory.create(recipe);
        Money unit = (product instanceof Priced p) ? p.price() : product.basePrice();
        Money subtotal = unit.multiply(qty);

        var result = pricing.price(subtotal);

        return quickReceiptPrinter.format(recipe, qty, result, taxPercent);
    }

    /**
     * The Week-10 expected API: checkout by order id.
     * Looks up an Order from an OrderRepository and formats the receipt using ReceiptFormatter.
     * Requires that this instance was constructed with a non-null OrderRepository and ReceiptFormatter.
     */
    public String checkout(long orderId) {

        if (orders == null || orderFormatter == null) {
            throw new IllegalStateException("Order-based checkout not configured. Provide OrderRepository and ReceiptFormatter in constructor.");
        }
        Order order = orders.findById(orderId).orElseThrow(() -> new IllegalArgumentException("order not found: " + orderId));
        // compute pricing pipeline from order subtotal
        var pr = pricing.price(order.subtotal());
        // Let the orderFormatter format items and totals (app-layer formatter)
        return orderFormatter.format(orderId, List.copyOf(order.items()), pr, taxPercent);
    }

    public PricingService.PricingResult priceOrder(long orderId) {

        if (orders == null) throw new IllegalStateException("OrderRepository not configured");
        Order order = orders.findById(orderId).orElseThrow(() -> new IllegalArgumentException("order not found: " + orderId));
        return pricing.price(order.subtotal());
    }

}
