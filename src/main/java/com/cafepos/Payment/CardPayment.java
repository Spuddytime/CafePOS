package com.cafepos.Payment;

import com.cafepos.domain.Order;

public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CardPayment(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank())
            throw new IllegalArgumentException("cardNumber required");
        this.cardNumber = cardNumber.replaceAll("\\s+", "");
    }

    @Override
    public void pay(Order order) {
        if (order == null) throw new IllegalArgumentException("order required");
        String last4 = cardNumber.length() >= 4
                ? cardNumber.substring(cardNumber.length() - 4)
                : cardNumber; // if shorter than 4, show what we have
        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card ****" + last4);
    }
}
