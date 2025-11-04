package com.cafepos.vendor.legacy;

public final class LegacyThermalPrinter {
    public void legacyPrint(byte[] payload) {
        // imagine ESC/POS over serial…
        System.out.println("[Legacy] printing bytes: " + payload.length);
    }
}
