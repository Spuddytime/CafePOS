package com.cafepos.checkout;

import com.cafepos.common.Money;
import com.cafepos.common.Priced;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.domain.Product;
import com.cafepos.factory.ProductFactory;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.Payment.PaymentStrategy;

public final class CheckoutService {
    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter printer;
    private final int taxPercent;

    public CheckoutService(ProductFactory factory,
                           PricingService pricing,
                           ReceiptPrinter printer,
                           int taxPercent) {
        this.factory = factory;
        this.pricing = pricing;
        this.printer = printer;
        this.taxPercent = taxPercent;
    }

    /** Build and return the receipt text */
    public String checkout(String recipe, int qty) {
        Product product = factory.create(recipe);
        if (qty <= 0) qty = 1;

        Money unit = (product instanceof Priced p) ? p.price() : product.basePrice();
        Money subtotal = unit.multiply(qty);
        var result = pricing.price(subtotal);

        return printer.format(recipe, qty, result, taxPercent);
    }

    /** Same as checkout(), and also triggers payment */
    public String checkoutAndPay(String recipe, int qty, PaymentStrategy strategy) {
        Product product = factory.create(recipe);
        if (qty <= 0) qty = 1;

        Money unit = (product instanceof Priced p) ? p.price() : product.basePrice();
        Money subtotal = unit.multiply(qty);
        var result = pricing.price(subtotal);
        String receipt = printer.format(recipe, qty, result, taxPercent);

        // Payment Strategy integration
        Order tmp = new Order(OrderIds.next());
        tmp.addItem(new LineItem(product, qty));
        tmp.pay(strategy);

        return receipt;
    }
}