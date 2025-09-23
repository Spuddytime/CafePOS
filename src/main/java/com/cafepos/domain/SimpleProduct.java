package com.cafepos.domain;

import com.cafepos.common.Money;

/**
 * TODO: enforce constructor contracts:
 *  - id not null/blank
 *  - name not null/blank
 *  - basePrice not null (and Money prevents negatives)
 */
public final class SimpleProduct implements Product {
    private final String id;
    private final String name;
    private final Money basePrice;

    public SimpleProduct(String id, String name, Money basePrice) {
        // TODO: validations here
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override public String id() { return id; }
    @Override public String name() { return name; }
    @Override public Money basePrice() { return basePrice; }
}
