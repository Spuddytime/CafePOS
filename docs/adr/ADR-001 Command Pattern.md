# ADR-001 — Use Command Pattern for POS Actions


## Context

In Week 8, we needed a way for a POS “remote” (buttons / UI) to trigger actions such as:

- Adding items to an order
- Paying for an order
- (Optionally) running multi-step “macros”
- Supporting **undo** of the last action

A naive approach would be to let the UI call methods directly on `Order` or `OrderService` (e.g. `order.addItem(...)`, `order.pay(...)`). This tightly couples the UI to business logic and makes it harder to add undo, history, or programmable buttons.

## Decision

We introduced the **Command pattern** in the `com.cafepos.command` package:

- `Command` interface (`execute()`, `undo()`)
- Concrete commands: `AddItemCommand`, `PayOrderCommand`, `MacroCommand`
- Receiver: `OrderService` (does the real work)
- Invoker: `PosRemote` (holds slots + history stack)

The UI (or demo) now only talks to `PosRemote` and `Command` objects, not the domain directly.

## Consequences

**Pros**

- **Decoupled UI & domain**: `PosRemote` knows nothing about orders, products, or payments.
- **Undo support** almost for free via the history stack.
- **Programmable buttons**: slots can be rebound to different commands at runtime.
- Easy to add new actions (e.g. `CancelOrderCommand`) without changing the invoker.

**Cons**

- More classes/indirection than simple method calls.
- Slightly harder to trace flow at first (button → command → service → domain).

Overall, Command makes the POS control flow more flexible and future-proof, which fits the project’s goal of exploring clean architecture and extensibility.
