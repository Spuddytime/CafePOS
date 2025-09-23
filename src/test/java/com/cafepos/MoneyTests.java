package com.cafepos;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTests {

    @Test void addition_works() {
        assertEquals(Money.of(5.00), Money.of(2.00).add(Money.of(3.00)));
    }

    @Test void multiplication_works() {
        assertEquals(Money.of(10.00), Money.of(2.50).multiply(4));
    }

    @Test void zero_factory_works() {
        assertEquals(Money.of(0.00), Money.zero());
    }

    @Test void negative_money_is_rejected() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(-1.00));
    }

    @Test void multiply_negative_qty_is_rejected() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(1.00).multiply(-2));
    }
}
