package com.cafepos.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object for currency with 2-decimal precision.
 * TODO: make immutable and enforce non-negative amounts.
 */
public final class Money implements Comparable<Money> {
    private final BigDecimal amount;

    /**
     * TODO:
     *  - validate value >= 0
     *  - store using BigDecimal with scale(2, HALF_UP)
     */
    public static Money of(double value) {
        throw new UnsupportedOperationException("TODO: Money.of");
    }

    /** TODO: return a Money representing 0.00 */
    public static Money zero() {
        throw new UnsupportedOperationException("TODO: Money.zero");
    }

    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        // TODO: enforce a >= 0
        // TODO: ensure scale(2) with HALF_UP rounding when assigning to field
        this.amount = a.setScale(2, RoundingMode.HALF_UP); // keep, but add invariant checks above
    }

    /** TODO: return new Money = this + other */
    public Money add(Money other) {
        throw new UnsupportedOperationException("TODO: Money.add");
    }

    /** TODO: return new Money = this * qty (qty >= 0) */
    public Money multiply(int qty) {
        throw new UnsupportedOperationException("TODO: Money.multiply");
    }

    // TODO: equals, hashCode, toString as needed by tests
    @Override public int compareTo(Money o) {
        throw new UnsupportedOperationException("TODO: Money.compareTo");
    }
    @Override public boolean equals(Object obj) {
        return super.equals(obj); // TODO: implement value equality on amount
    }
    @Override public int hashCode() {
        return super.hashCode(); // TODO
    }
    @Override public String toString() {
        return amount.toString(); // OK for now
    }
}
