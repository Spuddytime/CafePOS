package com.cafepos.menu;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Depth-first iterator over a Menu tree using a stack of child iterators. */
public final class CompositeIterator implements Iterator<MenuComponent> {
    private final Deque<Iterator<MenuComponent>> stack = new ArrayDeque<>();

    public CompositeIterator(Iterator<MenuComponent> rootChildren) {
        if (rootChildren != null) stack.push(rootChildren);
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            if (stack.peek().hasNext()) return true;
            stack.pop(); // current iterator exhausted → pop and continue
        }
        return false;
    }

    @Override
    public MenuComponent next() {
        if (!hasNext()) throw new NoSuchElementException();
        Iterator<MenuComponent> it = stack.peek();
        MenuComponent node = it.next();

        // If this node is a composite Menu, push its children iterator for DFS
        if (node instanceof Menu m) {
            stack.push(m.childrenIterator());
        }
        return node;
    }
}
