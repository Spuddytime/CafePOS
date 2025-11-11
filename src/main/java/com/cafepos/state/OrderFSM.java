package com.cafepos.state;

/** Order finite state machine (context). Delegates to current State. */
public final class OrderFSM {
    private State state;

    public OrderFSM() { this.state = new NewState(); } // start in NEW

    void set(State s) { this.state = s; }              // package-private setter for states
    public String status() { return state.name(); }

    public void pay()       { state.pay(this); }
    public void prepare()   { state.prepare(this); }
    public void markReady() { state.markReady(this); }
    public void deliver()   { state.deliver(this); }
    public void cancel()    { state.cancel(this); }
}
