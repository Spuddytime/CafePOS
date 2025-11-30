package com.cafepos.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class OrderFSMTest {
    @Test
    void happy_path_reaches_delivered() {
        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());
        fsm.pay();           // NEW -> PREPARING
        assertEquals("PREPARING", fsm.status());
        fsm.markReady();     // PREPARING -> READY
        assertEquals("READY", fsm.status());
        fsm.deliver();       // READY -> DELIVERED
        assertEquals("DELIVERED", fsm.status());
    }

    @Test
    void invalid_transition_keeps_state() {
        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());
        fsm.prepare();       // not allowed in NEW
        assertEquals("NEW", fsm.status());  // still NEW
    }
}
