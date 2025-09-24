package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.domain.*;
import com.cafepos.Payment.PaymentStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTest {

    @Test
    void payment_strategy_called() {
        var p = new SimpleProduct("A", "A", Money.of(5));
        var order = new Order(42);
        order.addItem(new LineItem(p, 1));

        final boolean[] called = {false};
        PaymentStrategy fake = o -> called[0] = true;

        order.pay(fake);

        assertTrue(called[0], "Payment strategy should be called");
    }
}
