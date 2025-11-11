package com.cafepos.menu;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class MenuIteratorTest {

    @Test
    void depth_first_traversal_exact_order() {
        // Build tree:
        // ROOT
        //  ├─ Drinks
        //  │   └─ Coffee
        //  │       └─ Espresso
        //  └─ Cookie
        Menu root   = new Menu("ROOT");
        Menu drinks = new Menu("Drinks");
        Menu coffee = new Menu("Coffee");

        root.add(drinks);
        drinks.add(coffee);
        coffee.add(new MenuItem("Espresso", Money.of(2.50), true));
        root.add(new MenuItem("Cookie", Money.of(1.20), true));

        // Collect names via iterator() only (depth-first, pre-order)
        List<String> namesInOrder = new ArrayList<>();
        Iterator<MenuComponent> it = root.iterator();
        while (it.hasNext()) {
            namesInOrder.add(it.next().name());
        }

        // Exact depth-first order
        List<String> expected = List.of("ROOT", "Drinks", "Coffee", "Espresso", "Cookie");
        assertEquals(expected, namesInOrder);
    }

    @Test
    void vegetarian_filter_returns_only_items_marked_true() {
        // ROOT
        //  ├─ Cheesecake (false)
        //  └─ Oat Cookie (true)
        Menu root = new Menu("ROOT");
        root.add(new MenuItem("Cheesecake", Money.of(3.50), false));
        root.add(new MenuItem("Oat Cookie", Money.of(1.20), true));

        List<String> vegNames = new ArrayList<>();
        Iterator<MenuComponent> it = root.iterator();
        while (it.hasNext()) {
            MenuComponent c = it.next();
            if (c instanceof MenuItem mi && mi.vegetarian()) {
                vegNames.add(mi.name());
            }
        }

        assertEquals(List.of("Oat Cookie"), vegNames);
    }
}
