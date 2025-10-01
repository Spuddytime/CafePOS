package com.cafepos.Payment;

import com.cafepos.domain.Order;

//Every order must have a payment strategy

    public interface  PaymentStrategy {

        void pay(Order order);
    }


