package com.cafepos.Payment;

// class Should not modify the order total it should only handle HOW the payment is made

import com.cafepos.domain.Order;

public final class CashPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        System.out.println("[Cash] Customer paid " +
                order.totalWithTax(10) + " EUR");
    }
}

