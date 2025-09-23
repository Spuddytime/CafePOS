package com.cafepos.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value object for currency with 2-decimal precision.
 * Immutable and enforces non-negative amounts.
 */
public final class Money implements Comparable<Money> {
    private final BigDecimal amount;

    /**
     * Create a Money from a double.
     * - Validates value >= 0
     * - Stores with scale(2, HALF_UP)
     */
    public static Money of(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        return new Money(BigDecimal.valueOf(value));
    }

    /** Return a Money representing 0.00 */
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        if (a.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }

    /** Return new Money = this + other */
    public Money add(Money other) {
        if (other == null) throw new IllegalArgumentException("other required");
        return new Money(this.amount.add(other.amount));
    }

    /** Return new Money = this * qty (qty >= 0) */
    public Money multiply(int qty) {
        if (qty < 0) throw new IllegalArgumentException("quantity cannot be negative");
        return new Money(this.amount.multiply(BigDecimal.valueOf(qty)));
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money other)) return false;
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
