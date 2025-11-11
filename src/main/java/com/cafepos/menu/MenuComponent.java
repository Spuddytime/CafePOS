package com.cafepos.menu;

import com.cafepos.common.Money;
import java.util.Iterator;

/**
 * Base type for both composite menus and leaf menu items.
 * By default, operations are unsupported (safe defaults).
 * Composites/Leaves will override what they actually support.
 */
public abstract class MenuComponent {

    // --- Composite-style operations (unsupported by default) ---
    public void add(MenuComponent c) {
        throw new UnsupportedOperationException("add not supported here");
    }

    public void remove(MenuComponent c) {
        throw new UnsupportedOperationException("remove not supported here");
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException("getChild not supported here");
    }

    // --- Leaf data (unsupported by default; leaves/composites override as needed) ---
    public String name() {
        throw new UnsupportedOperationException("name not supported here");
    }

    public Money price() {
        throw new UnsupportedOperationException("price not supported here");
    }

    /** Default: not vegetarian. Leaves may override. */
    public boolean vegetarian() { return false; }

    // --- Iteration / printing hooks (composites/leaves override appropriately) ---
    public Iterator<MenuComponent> iterator() {
        throw new UnsupportedOperationException("iterator not supported here");
    }

    public void print() {
        throw new UnsupportedOperationException("print not supported here");
    }
}
