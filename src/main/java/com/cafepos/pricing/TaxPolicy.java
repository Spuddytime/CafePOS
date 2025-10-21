package com.cafepos.pricing;

import com.cafepos.common.Money;

/** Computes tax on a given amount. */
public interface TaxPolicy {
    Money taxOn(Money amount);
}
