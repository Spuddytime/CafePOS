package com.cafepos;

import com.cafepos.common.*;
import com.cafepos.domain.*;
import com.cafepos.decorator.*;
import com.cafepos.factory.ProductFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryVsManualTest {

    @Test
    void factory_and_manual_build_same_drink() {
        Product viaFactory = new ProductFactory().create("ESP+SHOT+OAT+L");
        Product viaManual  = new SizeLarge(
                new OatMilk(
                        new ExtraShot(
                                new SimpleProduct("P-ESP", "Espresso", Money.of(2.50))
                        )));

        Money pf = ((Priced) viaFactory).price();
        Money pm = ((Priced) viaManual).price();

        System.out.println("\n--- factory_and_manual_build_same_drink ---");
        System.out.println("Factory name: " + viaFactory.name());
        System.out.println("Manual  name: " + viaManual.name());
        System.out.println("Factory price: " + pf);
        System.out.println("Manual  price: " + pm);

        assertEquals(viaManual.name(), viaFactory.name());
        assertEquals(pm, pf);

        Order a = new Order(2001); a.addItem(new LineItem(viaFactory, 1));
        Order b = new Order(2002); b.addItem(new LineItem(viaManual, 1));

        System.out.println("Order A subtotal: " + a.subtotal() + ", total(10%): " + a.totalWithTax(10));
        System.out.println("Order B subtotal: " + b.subtotal() + ", total(10%): " + b.totalWithTax(10));

        assertEquals(a.subtotal(), b.subtotal());
        assertEquals(a.totalWithTax(10), b.totalWithTax(10));
    }
}
