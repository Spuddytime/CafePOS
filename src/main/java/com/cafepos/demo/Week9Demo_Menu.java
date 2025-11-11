package com.cafepos.demo;

import com.cafepos.menu.Menu;
import com.cafepos.menu.MenuItem;
import com.cafepos.common.Money;

public final class Week9Demo_Menu {
    public static void main(String[] args) {
        // Sections
        Menu root    = new Menu("CAFÉ MENU");
        Menu drinks  = new Menu("Drinks");
        Menu coffee  = new Menu("Coffee");
        Menu desserts= new Menu("Desserts");

        // Coffee items (all V for demo)
        coffee.add(new MenuItem("Espresso",      Money.of(2.50), true));
        coffee.add(new MenuItem("Latte (Large)", Money.of(3.90), true));

        // Desserts
        desserts.add(new MenuItem("Cheesecake",  Money.of(3.50), false));
        desserts.add(new MenuItem("Oat Cookie",  Money.of(1.20), true));

        // Assemble tree
        drinks.add(coffee);
        root.add(drinks);
        root.add(desserts);

        // Print entire menu (depth-first)
        root.print();

        // Veg-only list (using our own helper below)
        System.out.println("\nVegetarian options:");
        for (MenuItem mi : vegetarianItems(root)) {
            System.out.println(" * " + mi.name() + " = " + mi.price());
        }
    }

    // Minimal helper so we don’t depend on extra menu methods:
    private static java.util.List<MenuItem> vegetarianItems(Menu root) {
        var out = new java.util.ArrayList<MenuItem>();
        var it = root.iterator();
        while (it.hasNext()) {
            var n = it.next();
            if (n instanceof MenuItem mi && mi.vegetarian()) out.add(mi);
        }
        return out;
    }
}
