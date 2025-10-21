package com.cafepos.pricing;

import com.cafepos.common.Money;

/** Fixed amount off (e.g., €1.00), capped at the subtotal. */
public final class FixedCouponDiscount implements DiscountPolicy {
    private final Money amount;
    public FixedCouponDiscount(Money amount) {
        if (amount == null) throw new IllegalArgumentException("amount required");
        this.amount = amount;
    }
    @Override public Money discountOf(Money subtotal) {
        return amount.asBigDecimal().compareTo(subtotal.asBigDecimal()) > 0
                ? subtotal
                : amount;
    }
}
