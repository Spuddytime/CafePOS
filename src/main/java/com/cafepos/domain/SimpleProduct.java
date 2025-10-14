package com.cafepos.domain;

import com.cafepos.common.Money;
import com.cafepos.common.Priced; //Week 5 work creeping in :(
public final class SimpleProduct implements Product, Priced {  // now also implements Priced
    private final String id;
    private final String name;
    private final Money basePrice;

    public SimpleProduct(String id, String name, Money basePrice) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (basePrice == null) throw new IllegalArgumentException("basePrice required");
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override public String id() { return id; }
    @Override public String name() { return name; }
    @Override public Money basePrice() { return basePrice; }

    // Week 5 addition
    @Override public Money price() {
        return basePrice;
    }
}
