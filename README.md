# Café POS

A small, test-driven **Point of Sale** app for a café, built incrementally across labs.  
You’ll find a clean domain model (Money, Products, Orders), pluggable **Payment Strategies** (Week 3), an **Observer** event flow (Week 4), and now **Decorator + Factory** for configurable drinks (Week 5).

> **Tech:** Java 21 • Maven • JUnit 5 • PlantUML

---

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

---


