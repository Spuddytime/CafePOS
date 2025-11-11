package com.cafepos.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Composite node: a menu section that can contain menus/items. */
public final class Menu extends MenuComponent {
    private final String name;
    private final List<MenuComponent> children = new ArrayList<>();

    public Menu(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.name = name;
    }

    // --- composite ops ---
    @Override public void add(MenuComponent c) { children.add(c); }
    @Override public void remove(MenuComponent c) { children.remove(c); }
    @Override public MenuComponent getChild(int i) { return children.get(i); }

    @Override public String name() { return name; }

    /** Iterator over direct children (used by CompositeIterator). */
    public Iterator<MenuComponent> childrenIterator() {
        return children.iterator();
    }

    /** Depth-first iterator over the whole subtree. */
    @Override public Iterator<MenuComponent> iterator() {
        return new CompositeIterator(childrenIterator());
    }

    /** Print this section then all descendants. */
    @Override public void print() {
        System.out.println(name);
        for (MenuComponent c : children) c.print();
    }
}
