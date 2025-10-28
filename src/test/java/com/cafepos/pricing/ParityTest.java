package com.cafepos.pricing;

import com.cafepos.checkout.CheckoutService;
import com.cafepos.factory.ProductFactory;
import com.cafepos.smells.OrderManagerGod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class ParityTest {
    @Test void lat_l_two_loyal5_matches_old() {
        String oldReceipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);

        var pricing  = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        var printer  = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);

        String newer = checkout.checkout("LAT+L", 2);
        assertEquals(oldReceipt, newer);
    }
}
