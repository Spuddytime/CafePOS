package com.cafepos.factory;

import com.cafepos.domain.Product;
import com.cafepos.common.Priced;

//This is a class for my own sanity just testing

public class FactoryCheck {
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();

        // Example 1 — Espresso with Extra Shot and Oat Milk
        Product p1 = factory.create("ESP+SHOT+OAT");
        System.out.println("Drink 1: " + p1.name());
        System.out.println("Price 1: " + ((Priced) p1).price());
        System.out.println();

        // Example 2 — Large Latte
        Product p2 = factory.create("LAT+L");
        System.out.println("Drink 2: " + p2.name());
        System.out.println("Price 2: " + ((Priced) p2).price());
        System.out.println();

        // Example 3 — Cappuccino with Syrup + Extra Shot
        Product p3 = factory.create("CAP+SYP+SHOT");
        System.out.println("Drink 3: " + p3.name());
        System.out.println("Price 3: " + ((Priced) p3).price());
    }
}
