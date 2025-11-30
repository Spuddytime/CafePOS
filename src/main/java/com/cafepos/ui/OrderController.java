package com.cafepos.ui;

import com.cafepos.app.CheckoutService;        // app-layer service
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderRepository;
import com.cafepos.factory.ProductFactory;

public final class OrderController {

    private final OrderRepository repo;
    private final CheckoutService checkout;
    private final ProductFactory factory = new ProductFactory();

    // default tax for the simple demo
    private final int defaultTaxPercent = 10;

    public OrderController(OrderRepository repo, CheckoutService checkout) {
        this.repo = repo;
        this.checkout = checkout;
    }

    public long createOrder(long id) {
        repo.save(new Order(id));
        return id;
    }

    public void addItem(long orderId, String recipe, int qty) {
        Order order = repo.findById(orderId).orElseThrow();
        order.addItem(new LineItem(factory.create(recipe), qty));
        repo.save(order);
    }

    // Used by Week10Demo_MVC – hides the tax choice from the UI
    public String checkout(long orderId) {
        return checkout.checkout(orderId, defaultTaxPercent);
    }

    // Overload if you ever want to specify tax from the UI
    public String checkout(long orderId, int taxPercent) {
        return checkout.checkout(orderId, taxPercent);
    }
}
