# Café POS

A small, test-driven **Point of Sale** app for a café, built incrementally across labs.  

> **Tech:** Java 21 • Maven • JUnit 5 • PlantUML

---

### Group 44

-Stephen Walsh (21334234)

-Piotr Pawlowski (21304858)

## ✨ What’s implemented so far

### Week 2 — Core Domain
- `Money` value type with safe arithmetic (`add`, `multiply`, `percent`).
- `Product` interface + `SimpleProduct`.
- `Order` and `LineItem` with subtotal/tax/total math.
- `Catalog` with `InMemoryCatalog`.

### Week 3 — Payment Strategy
- `PaymentStrategy` interface.
- Concrete strategies: `CashPayment`, `CardPayment`, `WalletPayment`.
- `Order.pay(strategy)` delegates to the chosen strategy (open for new payment options).

### Week 4 — Observer Pattern
- `Order` becomes a publisher (register/unregister/notify).
- Observers: `KitchenDisplay`, `DeliveryDesk`, `CustomerNotifier`.
- CLI demo (`Week4CLIDemo`) shows item add, pay, and ready events triggering observers.

### Week 5 — Decorator + Factory
- `Priced` interface (`price(): Money`) implemented by `SimpleProduct` and **all decorators**.
- `ProductDecorator` base class; add-ons: `ExtraShot`, `OatMilk`, `Syrup`, `SizeLarge`.
- `LineItem` now uses decorated unit `price()` when present.
- `ProductFactory.create("ESP+SHOT+OAT+L")` builds drinks from recipe tokens.
- CLI demo (`Week5Demo`) prints a receipt with decorated names and correct totals.

### Week 6 — Refactor (Pricing seam + Orchestrator)
- Locked current behavior with `Week6CharacterizationTests` (checks Subtotal/Discount/Tax/Total).
- Identified smells in `OrderManagerGod` (comments only): long method, duplicated math, primitive obsession, global state.
- Extracted pricing pieces:
  - Discounts: `DiscountPolicy` + `NoDiscount`, `LoyaltyPercentDiscount`, `FixedCouponDiscount`
  - Tax: `TaxPolicy` + `FixedRateTaxPolicy`
  - Pipeline: `PricingService` (returns a `PricingResult`)
  - Output: `ReceiptPrinter` (same receipt format)
- Added small orchestrator `CheckoutService` (build via `ProductFactory`, compute subtotal, price, print).
- Parity demo `Week6Demo`: old vs new **receipt text matches**.
- Extra unit tests: discount policies, tax policy, and pricing pipeline.

# Week 8 — Command + Adapter

### Command Pattern
- Added a generic `Command` interface (`execute()`, `undo()` default).
- Created `OrderService` (Receiver) — a clean façade over `Order`, `ProductFactory`, `PaymentStrategy`.
- Concrete commands:
    - `AddItemCommand`
    - `RemoveItemCommand` (via undo)
    - `PayOrderCommand`
    - **Optional:** `MacroCommand` (executes a sequence)
- `PosRemote` acts as the **Invoker** with configurable slots and an **undo stack**.
- `Week8Demo_Commands` shows:
    - Press add item
    - Press second add
    - Undo
    - Pay
    - Undo removes last action only

### Adapter Pattern
- Defined `Printer` interface (target).
- Legacy vendor printer: `LegacyThermalPrinter` (adaptee, accepts `byte[]`).
- Adapter: `LegacyPrinterAdapter` converts text → bytes → legacy print.
- `Week8Demo_Adapter` proves adapter works without modifying legacy code.

---

# Week 9 — Composite + Iterator + State

### Composite Menu
- `MenuComponent` abstract base (safe defaults throwing `UnsupportedOperationException`).
- `MenuItem` leaf — name, price, vegetarian flag.
- `Menu` composite — can contain menus or menu items.
- Depth-first traversal using **`CompositeIterator`** (iterator-of-iterators stack).
- `Week9Demo_Menu` prints full hierarchical café menu + lists **vegetarian items only**.

### Iterator
- Supports depth-first traversal over nested menus.
- `vegetarianItems()` uses iteration + filtering (streams).

### State Pattern
- Implemented order lifecycle using **object-based states**, not if/else.
- `OrderFSM` holds current `State` (NEW → PREPARING → READY → DELIVERED / CANCELLED).
- States:
    - `NewState`
    - `PreparingState`
    - `ReadyState`
    - `DeliveredState`
    - `CancelledState`
- Illegal transitions print messages (e.g., “Cannot prepare before pay”).
- `Week9Demo_State` shows lifecycle transitions working.

### State Transition Table
Summarises allowed/blocked transitions for Week 10 assessment.

---

# Week 10 — Layered Architecture, MVC, Components & Connectors

### Four-Layer Architecture
**Presentation → Application → Domain → Infrastructure**

- **Domain:** pure business logic (`Order`, `LineItem`, `Money`, States, Menu).
- **Application:** `CheckoutService` orchestrates pricing & receipt formatting.
- **Infrastructure:**
    - `InMemoryOrderRepository`
    - Legacy printer adapter
    - EventBus
- **Presentation/UI:**
    - `OrderController` (no formatting)
    - `ConsoleView` (printing only)

### MVC Console Demo
`Week10Demo_MVC` demonstrates:
- Create order
- Add items
- Checkout via application service
- View prints the receipt
- Proves UI is cleanly separated from logic.

### Components & Connectors (EventBus)
- Added lightweight `EventBus`:
    - `on(eventType, handler)`
    - `emit(event)`
- Events: `OrderCreated`, `OrderPaid`.
- `EventWiringDemo` shows UI reacting to events without tight coupling.

### Architecture Reflection
- Layered Monolith chosen for simplicity + maintainability.
- Clear seams for future microservices: Payments, Notifications, Printers, Orders.
- Future connectors: REST APIs or event streams.
- Current EventBus simulates asynchronous communication cleanly.

---


