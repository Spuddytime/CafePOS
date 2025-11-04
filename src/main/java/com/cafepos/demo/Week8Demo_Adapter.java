package com.cafepos.demo;

import com.cafepos.checkout.*;
import com.cafepos.pricing.FixedRateTaxPolicy;
import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.printing.LegacyPrinterAdapter;
import com.cafepos.printing.Printer;
import vendor.legacy.LegacyThermalPrinter;
import com.cafepos.common.Money;

public final class Week8Demo_Adapter {
    public static void main(String[] args) {
        // Fake a priced result (reuse your Week 6 pipeline if you want)
        var pricing = new PricingService(new LoyaltyPercentDiscount(0), new FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(7.80));
        var receipt = new ReceiptPrinter().format("LAT+L", 2, pr, 10);

        Printer printer = new LegacyPrinterAdapter(new LegacyThermalPrinter());
        printer.print(receipt);

        System.out.println("[Demo] Sent receipt via adapter.");
    }
}
