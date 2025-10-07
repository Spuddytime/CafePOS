package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.SimpleProduct;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverItemAddedTest {

    @Test
    void observers_notified_on_item_add() {
        var p = new SimpleProduct("A", "A", Money.of(2.00));
        var o = new Order(1);

        List<String> events = new ArrayList<>();
        o.register((order, evt) -> events.add(evt));

        o.addItem(new LineItem(p, 1));

        assertTrue(events.contains("itemAdded"));
    }
}
