package com.cafepos.ui;

import com.cafepos.domain.*;
import com.cafepos.factory.ProductFactory;
import com.cafepos.checkout.CheckoutService;

public final class OrderController {

    private final OrderRepository repo;
    private final CheckoutService checkout;
    private final ProductFactory factory = new ProductFactory();

    public OrderController(OrderRepository repo, CheckoutService checkout) {

        this.repo = repo;
        this.checkout = checkout;
    }

    public long createOrder(long id) {

        repo.save(new Order(id));
        return id;
    }

    /** Add a line item (qty + product) to an existing order */
    public void addItem(long orderId, String recipe, int qty) {

        Order order = repo.findById(orderId).orElseThrow();
        order.addItem(new LineItem(factory.create(recipe), qty));
        repo.save(order);
    }

    public String checkout(long orderId) {

        return checkout.checkout(orderId);
    }
}
