package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.domain.Product;

public class OrderManagerGod {

    // smell: shared global setting (other code can change this unexpectedly)
    public static int TAX_PERCENT = 10;

    // smell: shared global flag (last code used lives in a global spot)
    public static String LAST_DISCOUNT_CODE = null;

    // smell: one huge method doing many jobs (make product, price it, discount, tax, payment, and print)
    public static String process(String recipe, int qty, String paymentType,
                                 String discountCode, boolean printReceipt) {

        // smell: this method builds the product itself (should be given a helper instead of 'new' here)
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);

        // smell: type check and fallback logic lives here; ties this method to low-level details
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.common.Priced p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }

        // smell: hidden rule; quantity 0 or less becomes 1 (business rule buried in a big method)
        if (qty <= 0) qty = 1;

        Money subtotal = unitPrice.multiply(qty);

        // smell: discount is driven by raw strings and hard-coded numbers
        Money discount = Money.zero();
        if (discountCode != null) {
            if (discountCode.equalsIgnoreCase("LOYAL5")) {   // 5% is baked into the code
                discount = Money.of(subtotal.asBigDecimal()
                        .multiply(java.math.BigDecimal.valueOf(5))
                        .divide(java.math.BigDecimal.valueOf(100)));
            } else if (discountCode.equalsIgnoreCase("COUPON1")) { // €1 is baked into the code
                discount = Money.of(1.00);
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            // smell: remembers last code in a global place
            LAST_DISCOUNT_CODE = discountCode;
        }

        // smell: repeated low-level money math scattered here
        Money discounted = Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal()));
        if (discounted.asBigDecimal().signum() < 0) discounted = Money.zero();

        // smell: tax math is inlined and depends on the global percentage
        var tax = Money.of(discounted.asBigDecimal()
                .multiply(java.math.BigDecimal.valueOf(TAX_PERCENT))
                .divide(java.math.BigDecimal.valueOf(100)));

        var total = discounted.add(tax);

        // smell: big if/else on strings to decide payment behavior (grows every time a new type is added)
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }

        // smell: building the receipt text and printing are mixed into the core logic
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);

        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
