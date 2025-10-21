package com.cafepos.pricing;

import com.cafepos.common.Money;
import java.math.BigDecimal;

/** Percent off the subtotal (e.g., 5 -> 5%). */
public final class LoyaltyPercentDiscount implements DiscountPolicy {
    private final int percent;
    public LoyaltyPercentDiscount(int percent) {
        if (percent < 0) throw new IllegalArgumentException("percent >= 0");
        this.percent = percent;
    }
    @Override public Money discountOf(Money subtotal) {
        BigDecimal d = subtotal.asBigDecimal()
                .multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100));
        return Money.of(d);
    }
}
