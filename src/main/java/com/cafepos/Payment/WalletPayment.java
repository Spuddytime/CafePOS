package com.cafepos.Payment;

import com.cafepos.domain.Order;

public final class WalletPayment implements PaymentStrategy {
    private final String walletId;

    public WalletPayment(String walletId) {
        if (walletId == null || walletId.isBlank())
            throw new IllegalArgumentException("walletId required");
        this.walletId = walletId;
    }

    @Override
    public void pay(Order order) {
        if (order == null) throw new IllegalArgumentException("order required");
        System.out.println("[Wallet] Customer paid " + order.totalWithTax(10) + " EUR via wallet " + walletId);
    }
}
