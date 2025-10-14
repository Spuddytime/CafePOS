package com.cafepos.decorator;

import com.cafepos.domain.Product;
import com.cafepos.common.Money;
import com.cafepos.common.Priced;

public final class OatMilk extends ProductDecorator implements Priced {
    private static final Money SURCHARGE = Money.of(0.50);

    public OatMilk(Product base) { super(base); }

    @Override public String name() {
        return base.name() + " + Oat Milk";
    }

    @Override public Money price() {
        Money unit = (base instanceof Priced p) ? p.price() : base.basePrice();
        return unit.add(SURCHARGE);
    }
}
