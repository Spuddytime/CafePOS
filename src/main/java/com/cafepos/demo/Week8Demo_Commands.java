package com.cafepos.demo;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.Payment.CardPayment;
import com.cafepos.command.*;

/**
 * Binds buttons to commands and simulates presses + an undo.
 */
public final class Week8Demo_Commands {
    public static void main(String[] args) {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(3);

        // Slot 0: add "ESP+SHOT+OAT" x1
        remote.setSlot(0, new AddItemCommand(service, "ESP+SHOT+OAT", 1));
        // Slot 1: add "LAT+L" x2
        remote.setSlot(1, new AddItemCommand(service, "LAT+L", 2));
        // Slot 2: pay with card at 10% tax
        remote.setSlot(2, new PayOrderCommand(service, new CardPayment("1234 5678 9012 3456"), 10));

        remote.press(0); // add espresso + shot + oat x1
        remote.press(1); // add large latte x2
        remote.undo();   // remove last item (undo slot 1)
        remote.press(1); // add large latte x2 again
        remote.press(2); // pay with card
    }
}
