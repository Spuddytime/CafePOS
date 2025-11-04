package com.cafepos.command;

import com.cafepos.domain.*;
import com.cafepos.Payment.PaymentStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class PayOrderCommandTest {

    /** Simple spy to prove the strategy was invoked. */
    static final class SpyPayment implements PaymentStrategy {
        boolean called = false;
        @Override public void pay(Order order) { called = true; }
    }

    @Test
    void pay_invokes_strategy() {
        Order order = new Order(OrderIds.next());
        OrderService svc = new OrderService(order);

        // Give the order something to pay.
        new AddItemCommand(svc, "ESP", 2).execute(); // 2 x 2.50

        SpyPayment spy = new SpyPayment();
        new PayOrderCommand(svc, spy, 10).execute();

        assertTrue(spy.called, "PaymentStrategy should be invoked by PayOrderCommand");
    }
}
