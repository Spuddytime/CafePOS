package com.cafepos.factory;

import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.decorator.*;

public final class ProductFactory {
    public Product create(String recipe) {
        if (recipe == null || recipe.isBlank())
            throw new IllegalArgumentException("recipe required");

        //Trim whitespace and convert to uppercase so "esp + oat" also works
        String[] parts = java.util.Arrays.stream(recipe.split("\\+"))
                .map(String::trim)
                .map(String::toUpperCase)
                .toArray(String[]::new);

        //Select our Base product here
        Product p = switch (parts[0]) {
            case "ESP" -> new SimpleProduct("P-ESP", "Espresso",  Money.of(2.50));
            case "LAT" -> new SimpleProduct("P-LAT", "Latte",     Money.of(3.20));
            case "CAP" -> new SimpleProduct("P-CAP", "Cappuccino",Money.of(3.00));
            default -> throw new IllegalArgumentException("Unknown base: " + parts[0]);
        };

        for (int i = 1; i < parts.length; i++) {
            String t = parts[i];
            p = switch (t) {
                case "SHOT" -> new ExtraShot(p);    // + Extra Shot (adds 0.80)
                case "OAT"  -> new OatMilk(p);      // + Oat Milk (adds 0.50)
                case "SYP"  -> new Syrup(p);        // + Syrup (adds 0.40)
                case "L"    -> new SizeLarge(p);    // Large size (adds 0.70)
                default -> throw new IllegalArgumentException("Unknown addon: " + t);
            };
        }

        // Return the fully composed product
        return p;
    }
}
