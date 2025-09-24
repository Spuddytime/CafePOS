package com.cafepos.Payment;

import com.cafepos.domain.Order;

public final class CashPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        if (order == null) throw new IllegalArgumentException("order required");
        System.out.println("[Cash] Customer paid " + order.totalWithTax(10) + " EUR");
    }
}
