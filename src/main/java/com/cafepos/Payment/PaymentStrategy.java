package com.cafepos.Payment;

import com.cafepos.domain.Order;

    public interface  PaymentStrategy {

        void pay(Order order);
    }


