package com.cafepos.command;
import com.cafepos.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class CommandsFlowTest {

    @Test
    void add_add_undo_affects_subtotal() {
        Order order = new Order(OrderIds.next());
        OrderService svc = new OrderService(order);
        PosRemote remote = new PosRemote(2);

        // slot 0: ESP+SHOT (2.50 + 0.80 = 3.30)
        remote.setSlot(0, new AddItemCommand(svc, "ESP+SHOT", 1));
        // slot 1: LAT+L (3.20 + 0.70 = 3.90)
        remote.setSlot(1, new AddItemCommand(svc, "LAT+L", 1));

        remote.press(0); // +3.30
        remote.press(1); // +3.90
        assertEquals("7.20", order.subtotal().toString());

        remote.undo();   // undo last add (LAT+L)
        assertEquals("3.30", order.subtotal().toString());
    }
}
