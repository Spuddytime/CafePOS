package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.decorator.*;
import com.cafepos.common.Priced;
import com.cafepos.domain.*;
import com.cafepos.factory.ProductFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecoratorTests {

    @Test
    void decorator_single_addon() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);

        System.out.println("\n--- decorator_single_addon ---");
        System.out.println("Name:  " + withShot.name());
        System.out.println("Price: " + ((Priced) withShot).price());

        assertEquals("Espresso + Extra Shot", withShot.name());
        assertEquals(Money.of(3.30), ((Priced) withShot).price());
    }

    @Test
    void decorator_stacks() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));

        System.out.println("\n--- decorator_stacks ---");
        System.out.println("Name:  " + decorated.name());
        System.out.println("Price: " + ((Priced) decorated).price());

        assertEquals("Espresso + Extra Shot + Oat Milk (Large)", decorated.name());
        assertEquals(Money.of(4.50), ((Priced) decorated).price());
    }

    @Test
    void factory_parses_recipe() {
        Product p = new ProductFactory().create("ESP+SHOT+OAT");

        System.out.println("\n--- factory_parses_recipe ---");
        System.out.println("Built: " + p.name());
        System.out.println("Price: " + ((Priced) p).price());

        assertTrue(p.name().contains("Espresso") && p.name().contains("Oat Milk"));
    }

    @Test
    void order_uses_decorated_price() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso); // 3.30 each
        Order o = new Order(1);
        o.addItem(new LineItem(withShot, 2));

        System.out.println("\n--- order_uses_decorated_price ---");
        System.out.println("Line total (2 x 3.30): " + o.subtotal());

        assertEquals(Money.of(6.60), o.subtotal());
    }
}
