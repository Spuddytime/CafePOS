package com.cafepos.demo;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.common.Money;
import com.cafepos.domain.*;
import com.cafepos.Payment.*;

public final class Week3Demo {

    /**
     * Week 3 demo — Strategy pattern:
     * Part A: Different orders use different payment strategies (Cash, Card, Wallet).
     * Part B: The same order is paid multiple times with different strategies,
     *         proving Order delegates and does not need if/else for payment types.
     */
    public static void main(String[] args) {
        // --- Setup catalog (same as Week 2) ---
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));
        catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50)));

        int taxPct = 10;

        // =========================
        // Part A: Different orders, different strategies
        // =========================

        // Cash payment
        Order cashOrder = new Order(OrderIds.next());
        cashOrder.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        cashOrder.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
        System.out.println("Order #" + cashOrder.id() + " Total: " + cashOrder.totalWithTax(taxPct));
        cashOrder.pay(new CashPayment());

        // Card payment
        Order cardOrder = new Order(OrderIds.next());
        cardOrder.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        cardOrder.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
        System.out.println("Order #" + cardOrder.id() + " Total: " + cardOrder.totalWithTax(taxPct));
        cardOrder.pay(new CardPayment("1234 5678 1234 1234"));

        // Wallet payment
        Order walletOrder = new Order(OrderIds.next());
        walletOrder.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        walletOrder.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
        System.out.println("Order #" + walletOrder.id() + " Total: " + walletOrder.totalWithTax(taxPct));
        walletOrder.pay(new WalletPayment("demo-wallet-01"));

        // =========================
        // Part B: Same order, multiple strategies (Reflection Q2)
        // =========================
        Order sameOrder = new Order(OrderIds.next());
        sameOrder.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        sameOrder.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));

        System.out.println("Order #" + sameOrder.id() + " Total: " + sameOrder.totalWithTax(taxPct));
        // Reflection Q2: same Order instance, different strategies — Order code unchanged.
        sameOrder.pay(new CashPayment());
        sameOrder.pay(new CardPayment("9999 8888 7777 1234"));
        sameOrder.pay(new WalletPayment("demo-wallet-02"));
    }
}
