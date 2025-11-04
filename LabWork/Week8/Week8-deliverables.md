## Week 8 — Command + Adapter

This week adds two behavior-centric patterns **without changing the core POS model**:

- **Command** decouples button presses (UI/invoker) from domain work (receiver) and gives us **undo** (+ optional **macro**).
- **Adapter** lets us print receipts to a **legacy thermal printer** without touching our checkout or domain code.

---

### Part A — Command Pattern

**What’s new**

- `com.cafepos.command.Command` – tiny protocol: `execute()` (+ optional `undo()`).
- **Receiver**: `OrderService`
    - `addItem(String recipe, int qty)` → builds via `ProductFactory`, adds `LineItem` to `Order`.
    - `removeLastItem()` → inverse used by `undo()`.
    - `pay(PaymentStrategy, int taxPercent)` → reuses Week-3 strategies.
    - `totalWithTax(int)` helper for demos/tests.
- **Concrete Commands**
    - `AddItemCommand(OrderService, recipe, qty)` – `execute()` add; `undo()` removes last.
    - `PayOrderCommand(OrderService, PaymentStrategy, tax%)` – `execute()` pay.
- **Invoker**
    - `PosRemote(int slots)` – `setSlot(i, Command)`, `press(i)` (executes + pushes history), `undo()`.
- **(Optional)** `MacroCommand(Command... steps)` – runs a sequence and undoes in reverse order.

**Demo**

- `demo/Week8Demo_Commands`
    - Bind: slot0=`AddItem(ESP+SHOT+OAT,1)`, slot1=`AddItem(LAT+L,2)`, slot2=`PayOrder(Card,10%)`.
    - Press: `0`, `1`, `undo()`, `1`, `2`.
    - Expected console: “Added …”, “Removed last item”, card line, “Payment processed …”.

**Tests**

- `CommandsFlowTest` – press add twice, `undo()`, assert exactly one item was reverted (count items/subtotal).
- `PayOrderCommandTest` – pressing the pay slot calls the payment strategy (assert printed line or spy).
- `MacroCommandTest` (if included) – macro of two adds, `undo()` once → one add remains.

**Why it matters**

- **Decoupling**: UI wiring (remote) knows nothing about Orders.
- **Undo**: captured request + inverse on receiver.
- **OCP**: new buttons = new commands; no edits to remote/domain.
---

### Part B — Adapter Pattern

**What’s new**

- `printing.Printer` – target interface our app expects: `void print(String receiptText)`.
- `vendor.legacy.LegacyThermalPrinter` – adaptee with `legacyPrint(byte[])`.
- `printing.LegacyPrinterAdapter` – converts `String` → UTF-8 `byte[]`, calls `legacyPrint`.

**Demo**

- `demo/Week8Demo_Adapter`
    - Build/obtain receipt text (or use `ReceiptPrinter` from Week 6).
    - `Printer p = new LegacyPrinterAdapter(new LegacyThermalPrinter());`
    - `p.print(receipt);`
    - Expected: `[Legacy] printing bytes: N` then `[Demo] Sent receipt via adapter.]`

**Tests**

- `AdapterTest` – fake legacy printer captures payload length; `print("ABC")` ⇒ length ≥ 3.

**Why it matters**

- Integrates a vendor device **without** changing POS domain or receipt code.
- Keeps checkout stable while swapping real printers later.

---


