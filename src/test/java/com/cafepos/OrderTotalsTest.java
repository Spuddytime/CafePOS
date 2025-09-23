package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.SimpleProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTotalsTest {

    @Test
    void order_totals_match_demo() {
        var p1 = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)); //P-ESP stands for Product-Espresso
        var p2 = new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50));

        var order = new Order(1);
        order.addItem(new LineItem(p1, 2)); // 2 * 2.50 = 5.00
        order.addItem(new LineItem(p2, 1)); // 1 * 3.50 = 3.50

        assertEquals(Money.of(8.50), order.subtotal());
        assertEquals(Money.of(0.85), order.taxAtPercent(10));
        assertEquals(Money.of(9.35), order.totalWithTax(10));
    }
}
