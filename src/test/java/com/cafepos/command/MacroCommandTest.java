package com.cafepos.command;

import com.cafepos.domain.Order;
import com.cafepos.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class MacroCommandTest {

    @Test
    void macro_exec_then_undo_in_reverse_order() {
        Order order = new Order(OrderIds.next());
        OrderService svc = new OrderService(order);

        Command addEsp   = new AddItemCommand(svc, "ESP",   1); // 2.50
        Command addLatL  = new AddItemCommand(svc, "LAT+L", 1); // 3.90

        Command combo = new MacroCommand(addEsp, addLatL);

        combo.execute();
        assertEquals("6.40", order.subtotal().toString());

        combo.undo(); // undo addLatL then addEsp
        assertEquals("0.00", order.subtotal().toString());
    }
}
