package com.cafepos.pricing;

import com.cafepos.common.Money;

/** No discount sadly for the customer :( */
public final class NoDiscount implements DiscountPolicy {
    @Override public Money discountOf(Money subtotal) { return Money.zero(); }
}
