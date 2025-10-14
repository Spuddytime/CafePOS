package com.cafepos.demo;

import com.cafepos.domain.Product;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;

public final class Week5Demo {
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();

        // Per lab sheet: build two drinks from recipes
        Product p1 = factory.create("ESP+SHOT+OAT"); // Espresso + Extra Shot + Oat Milk
        Product p2 = factory.create("LAT+L");        // Latte (Large)

        // Create an order and add them
        Order order = new Order(2001);               // or OrderIds.next() if you have it
        order.addItem(new LineItem(p1, 1));
        order.addItem(new LineItem(p2, 2));

        // Print the receipt
        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name()
                    + " x" + li.quantity()
                    + " = " + li.lineTotal());
        }
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }
}
