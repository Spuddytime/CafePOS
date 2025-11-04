package com.cafepos.command;

/**
 * Runs several commands as one.
 * execute(): runs steps in order.
 * undo():    undoes in reverse order.
 */
public final class MacroCommand implements Command {
    private final Command[] steps;

    public MacroCommand(Command... steps) {
        this.steps = steps;
    }

    @Override
    public void execute() {
        for (Command c : steps) c.execute();
    }

    @Override
    public void undo() {
        for (int i = steps.length - 1; i >= 0; i--) steps[i].undo();
    }
}
