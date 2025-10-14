package com.cafepos.demo;

import com.cafepos.common.Money;
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.domain.*;
import com.cafepos.Payment.CashPayment;
import com.cafepos.Payment.CardPayment;
import com.cafepos.Payment.WalletPayment;

import java.util.Scanner;

public final class Week4CLIDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Build a tiny in-memory catalog
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));
        catalog.add(new SimpleProduct("P-LAT", "Latte",    Money.of(3.00)));
        catalog.add(new SimpleProduct("P-MOC", "Mocha",    Money.of(3.20)));

        // Create an order (use your OrderIds if you want)
        var order = new Order(1006);

        // Register observers
        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());

        System.out.println("=== Café POS — Week 4 CLI Demo ===");

        boolean running = true;
        while (running) {
            System.out.println("""
                    
                    Choose an action:
                    1) Add item
                    2) Pay
                    3) Mark ready
                    4) Show totals
                    5) Exit
                    """);
            System.out.print("Select: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1" -> {
                    System.out.println("Available products:");
                    System.out.println("  P-ESP  Espresso  €2.50");
                    System.out.println("  P-LAT  Latte     €3.00");
                    System.out.println("  P-MOC  Mocha     €3.20");

                    System.out.print("Enter product ID: ");
                    String id = sc.nextLine().trim().toUpperCase();

                    var productOpt = catalog.findById(id);
                    if (productOpt.isEmpty()) {
                        System.out.println("Invalid product ID.");
                        continue;
                    }

                    System.out.print("Quantity: ");
                    int qty;
                    try { qty = Integer.parseInt(sc.nextLine().trim()); }
                    catch (NumberFormatException e) { System.out.println("Invalid quantity."); continue; }

                    try {
                        order.addItem(new LineItem(productOpt.get(), qty));
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
                case "2" -> {
                    System.out.println("Select payment method: 1) Cash  2) Card  3) Wallet");
                    System.out.print("Pay with: ");
                    String payChoice = sc.nextLine().trim();

                    switch (payChoice) {
                        case "1" -> order.pay(new CashPayment());
                        case "2" -> {
                            System.out.print("Enter last 4 digits: ");
                            String last4 = sc.nextLine().trim();
                            order.pay(new CardPayment("****" + last4));
                        }
                        case "3" -> {
                            System.out.print("Enter wallet id: ");
                            String wid = sc.nextLine().trim();
                            order.pay(new WalletPayment(wid));
                        }
                        default -> System.out.println("Invalid payment method.");
                    }
                }
                case "3" -> order.markReady();
                case "4" -> {
                    var subtotal = order.subtotal();
                    var tax = order.taxAtPercent(10);
                    var total = order.totalWithTax(10);
                    System.out.println("Subtotal: " + subtotal);
                    System.out.println("Tax(10%): " + tax);
                    System.out.println("Total:    " + total);
                }
                case "5" -> {
                    System.out.println("Exiting Café POS demo.");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
