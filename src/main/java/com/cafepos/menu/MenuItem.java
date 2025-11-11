package com.cafepos.menu;

import com.cafepos.common.Money;
import java.util.Collections;
import java.util.Iterator;

/** Leaf node: a single menu item (no children). */
public final class MenuItem extends MenuComponent {
    private final String name;
    private final Money price;
    private final boolean vegetarian;

    public MenuItem(String name, Money price, boolean vegetarian) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (price == null) throw new IllegalArgumentException("price required");
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    @Override public String name() { return name; }
    @Override public Money price() { return price; }
    @Override public boolean vegetarian() { return vegetarian; }

    /** Leaves have no children → empty iterator. */
    @Override public Iterator<MenuComponent> iterator() {
        return Collections.emptyIterator();
    }

    @Override public void print() {
        String veg = vegetarian ? " (V)" : "";
        System.out.println(" - " + name + veg + " = " + price);
    }
}
