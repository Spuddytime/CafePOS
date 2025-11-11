# Week 9 — Iterator + Composite + State (Deliverables)

This week extends the Café POS with **Composite + Iterator** (hierarchical menus with depth-first traversal & filtering) and a **State** machine for an order’s lifecycle. The goal is uniform tree navigation and clean lifecycle transitions without long `if/else` chains.

---

## What Was Added

### Part A — Composite + Iterator (Menu)

**Packages & classes**
- `com.cafepos.menu`
    - `MenuComponent` — abstract base (safe defaults throw `UnsupportedOperationException`).
    - `MenuItem` — **leaf**: `name()`, `price()`, `vegetarian()`, `iterator()` returns empty iterator.
    - `Menu` — **composite**: holds children, supports `add/remove/getChild`.
        - `childrenIterator()` → iterator over direct children.
        - `iterator()` → **depth-first** iterator that **starts at the root** (via `CompositeIterator`).
        - `print()` → prints section + all descendants.
    - `CompositeIterator` — DFS over nested menus using a **stack of iterators**.

**Why this matters**
- Treats **categories** and **items** uniformly (same API).
- Clients can **iterate** an entire tree without exposing internal lists.
- Depth-first traversal enables easy **filtering** (e.g., vegetarian-only).

---

### Part B — State (Order FSM)

**Packages & classes**
- `com.cafepos.state`
    - `State` — interface (`pay`, `prepare`, `markReady`, `deliver`, `cancel`, `name`).
    - `OrderFSM` — context; holds a current `State` and delegates to it.
    - Concrete states:
        - `NewState` → `pay` → `PreparingState`; `cancel` → `CancelledState`
        - `PreparingState` → `markReady` → `ReadyState`; `cancel` → `CancelledState`
        - `ReadyState` → `deliver` → `DeliveredState`
        - `DeliveredState` / `CancelledState` → terminal (other actions are rejected)

**Why this matters**
- Replaces tangled conditionals with **polymorphism**.
- Legal/illegal transitions are **localized** per state.
- Easier to add new states/behavior later (**OCP**, **SRP**).

---

## Demos

### `Week9Demo_Menu`
- Builds a menu tree: **CAFÉ MENU → Drinks → Coffee → Espresso/Latte**, **Desserts → Cheesecake/Oat Cookie**.
- Prints the whole menu; then prints vegetarian items only.

### `Week9Demo_State`

## Tests (JUnit 5)

- **Composite/Iterator**
    - Depth-first order includes the **root first** (e.g., `ROOT, Drinks, Coffee, Espresso, Cookie`).
    - Vegetarian filter returns only `(V)` items.
    - Edge cases (optional): empty menu; menu with only leaves.

- **State**
    - **Happy path**: `NEW → PREPARING → READY → DELIVERED` via `pay`, `markReady`, `deliver`.
    - **Invalid transitions** keep the state unchanged (e.g., `NEW.prepare()` stays `NEW`).

---

## State Transition Table

| State \ Event | pay | prepare | markReady | deliver | cancel |
|---|---|---|---|---|---|
| **NEW** | ✓ → PREPARING | ✗ (stay NEW) | ✗ (stay NEW) | ✗ (stay NEW) | ✓ → CANCELLED |
| **PREPARING** | ✗ (stay PREPARING) | “still preparing” (stay) | ✓ → READY | ✗ (stay) | ✓ → CANCELLED |
| **READY** | ✗ (stay) | ✗ (stay) | “already ready” (stay) | ✓ → DELIVERED | ✗ (stay) |
| **DELIVERED** | ✗ (stay) | ✗ (stay) | ✗ (stay) | “already delivered” (stay) | ✗ (stay) |
| **CANCELLED** | ✗ (stay) | ✗ (stay) | ✗ (stay) | ✗ (stay) | “already cancelled” (stay) |

**Legend:** ✓ → NEXT means transition allowed; ✗ means rejected & state unchanged.

---

## Reflection (short)

- **Composite + Iterator** gave us a uniform API over a tree structure and safe depth-first traversal using a stacked iterator, enabling simple filtering without exposing internal lists.
- **State** localized lifecycle rules inside states, removing fragile conditionals and making extensions (new states/paths) low-risk.
- We prioritized **safety** in `MenuComponent` (unsupported ops by default) and **transparency** through the uniform interface, striking a balance that prevents misuse but keeps the API easy to discover.

