package com.cafepos.decorator;

import com.cafepos.domain.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.common.Priced;

public class DecoratorCheck {
    public static void main(String[] args) {
        // base product
        var espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));

        // stacked decorators (wrapping each other)
        var decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));

        System.out.println(decorated.name());
        System.out.println(((Priced) decorated).price());
    }
}
