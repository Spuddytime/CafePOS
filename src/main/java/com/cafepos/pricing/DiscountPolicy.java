package com.cafepos.pricing;

import com.cafepos.common.Money;

/** Decides how much to take off a given subtotal. */
public interface DiscountPolicy {
    Money discountOf(Money subtotal);
}
