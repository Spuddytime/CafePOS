package com.cafepos.domain;

import com.cafepos.common.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODOs:
 *  - addItem: validate li != null and li.quantity() > 0
 *  - taxAtPercent: compute subtotal * (percent / 100), round to 2 decimals
 *  - totalWithTax: subtotal + tax
 */
public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) { this.id = id; }

    public long id() { return id; }
    public List<LineItem> items() { return Collections.unmodifiableList(items); }

    public void addItem(LineItem li) {
        if (li == null) {
            throw new IllegalArgumentException("lineItem required");
        }
        if (li.quantity() <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        items.add(li);
    }

    public Money subtotal() {
        return items.stream()
                .map(LineItem::lineTotal)
                .reduce(Money.zero(), Money::add);
    }

    public Money taxAtPercent(int percent) {
        return subtotal().percent(percent);
    }

    public Money totalWithTax(int percent) {
        return subtotal().add(taxAtPercent(percent));
    }

}
