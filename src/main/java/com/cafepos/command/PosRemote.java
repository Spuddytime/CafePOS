package com.cafepos.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker: holds button slots and an undo history.
 * It doesn’t know domain details—just calls Command.execute()/undo().
 */
public final class PosRemote {
    private final Command[] slots;
    private final Deque<Command> history = new ArrayDeque<>();

    public PosRemote(int n) { this.slots = new Command[n]; }

    /** Bind a command to a button slot. */
    public void setSlot(int i, Command c) { this.slots[i] = c; }

    /** Simulate pressing a button. */
    public void press(int i) {
        Command c = slots[i];
        if (c == null) {
            System.out.println("[Remote] No command in slot " + i);
            return;
        }
        c.execute();
        history.push(c);
    }

    /** Undo the last executed command (if it supports undo). */
    public void undo() {
        if (history.isEmpty()) {
            System.out.println("[Remote] Nothing to undo");
            return;
        }
        history.pop().undo();
    }
}
