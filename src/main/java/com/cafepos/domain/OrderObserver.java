package com.cafepos.domain;

//  new observers can be created free

public interface OrderObserver {
    void updated(Order order, String eventType);

}
