package com.cafepos.command;

import com.cafepos.Payment.PaymentStrategy;

/** Command: delegates payment to the receiver using a given strategy and tax rate. */
public final class PayOrderCommand implements Command {
    private final OrderService service;
    private final PaymentStrategy strategy;
    private final int taxPercent;

    public PayOrderCommand(OrderService service, PaymentStrategy strategy, int taxPercent) {
        this.service = service;
        this.strategy = strategy;
        this.taxPercent = taxPercent;
    }

    @Override public void execute() { service.pay(strategy, taxPercent); }
}
