package com.cafepos.demo;

import com.cafepos.common.Money;
import com.cafepos.domain.*;
import com.cafepos.factory.ProductFactory;
import com.cafepos.pricing.*;
import com.cafepos.smells.OrderManagerGod;
import com.cafepos.Payment.*;
import com.cafepos.checkout.CheckoutService;

import java.util.Locale;
import java.util.Scanner;

public final class MidTermDemo {

    private static final int TAX_PERCENT = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Café POS — Midterm Interactive Demo ===");
        System.out.println("Enter recipe tokens (examples):");
        System.out.println("  ESP           (Espresso)");
        System.out.println("  LAT+L         (Large Latte)");
        System.out.println("  ESP+SHOT+OAT  (Espresso + Extra Shot + Oat Milk)");
        System.out.print("Recipe: ");
        String recipe = sc.nextLine().trim();

        System.out.print("Quantity: ");
        int qty = 1;
        try { qty = Math.max(1, Integer.parseInt(sc.nextLine().trim())); } catch (Exception ignore) {}

        System.out.print("Discount [NONE | LOYAL5 | COUPON1]: ");
        String discountCode = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        DiscountPolicy discount = switch (discountCode) {
            case "LOYAL5" -> new LoyaltyPercentDiscount(5);
            case "COUPON1" -> new FixedCouponDiscount(Money.of(1.00));
            default -> {
                discountCode = "NONE";
                yield new NoDiscount();
            }
        };

        System.out.print("Payment [CASH | CARD | WALLET]: ");
        String payChoice = sc.nextLine().trim().toUpperCase(Locale.ROOT);

        // Clean path
        var pricing  = new PricingService(discount, new FixedRateTaxPolicy(TAX_PERCENT));
        var printer  = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, TAX_PERCENT);

        String cleanReceipt = checkout.checkout(recipe, qty);

        // Smelly path (Week 6 characterization) to prove parity
        // We pass the user's choices as strings to the God method, but don't actually rely on it for payment.
        String oldReceipt = OrderManagerGod.process(
                recipe, qty, payChoice, discountCode, false);

        // Show parity
        System.out.println("\n--- Old Receipt (God class) ---");
        System.out.println(oldReceipt);
        System.out.println("\n--- New Receipt (Clean path) ---");
        System.out.println(cleanReceipt);
        System.out.println("\nMatch: " + oldReceipt.equals(cleanReceipt));

        // This will help us show off observers
        System.out.print("\nPerform payment now? [y/N]: ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            ProductFactory factory = new ProductFactory();
            Product product = factory.create(recipe);

            Order order = new Order(OrderIds.next());
            order.register(new KitchenDisplay());
            order.register(new DeliveryDesk());
            order.register(new CustomerNotifier());
            order.addItem(new LineItem(product, qty));

            PaymentStrategy strategy = switch (payChoice) {
                case "CARD" -> {
                    System.out.print("Enter card number (or last 4): ");
                    String card = sc.nextLine().trim();
                    yield new CardPayment(card);
                }
                case "WALLET" -> {
                    System.out.print("Enter wallet id: ");
                    String wid = sc.nextLine().trim();
                    yield new WalletPayment(wid);
                }
                default -> new CashPayment();
            };

            System.out.println("Order #" + order.id() + " Total (with tax " + TAX_PERCENT + "%): " +
                    order.totalWithTax(TAX_PERCENT));
            order.pay(strategy);

            System.out.print("Mark order ready? [y/N]: ");
            if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                order.markReady();
            }
        }

        System.out.println("\nDone. Good luck!");
    }
}
