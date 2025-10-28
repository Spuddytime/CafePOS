package com.cafepos.pricing;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class DiscountPolicyTest {

    @Test void no_discount_is_zero() {
        assertEquals(Money.zero(), new NoDiscount().discountOf(Money.of(10.00)));
    }

    @Test void loyalty_5_percent_of_7_80_is_0_39() {
        assertEquals(Money.of(0.39), new LoyaltyPercentDiscount(5).discountOf(Money.of(7.80)));
    }

    @Test void fixed_coupon_is_capped_by_subtotal() {
        var coupon = new FixedCouponDiscount(Money.of(1.00));
        assertEquals(Money.of(1.00), coupon.discountOf(Money.of(3.30))); // normal
        assertEquals(Money.of(0.50), coupon.discountOf(Money.of(0.50))); // capped
        assertEquals(Money.of(1.00), coupon.discountOf(Money.of(1.00))); // exactly equal
    }
}
