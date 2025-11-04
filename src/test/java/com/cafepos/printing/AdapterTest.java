package com.cafepos.printing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import vendor.legacy.LegacyThermalPrinter;

final class AdapterTest {

    static final class FakeLegacy extends LegacyThermalPrinter {
        int lastLen = -1;
        @Override public void legacyPrint(byte[] payload) { lastLen = payload.length; }
    }

    @Test
    void adapter_converts_text_to_bytes() {
        var fake = new FakeLegacy();
        Printer p = new LegacyPrinterAdapter(fake);

        p.print("Order (ESP) x1\nTotal: 2.75");

        assertTrue(fake.lastLen >= 5, "Adapter should send some bytes to the legacy printer");
    }
}
