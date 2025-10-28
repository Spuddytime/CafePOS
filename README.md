# Café POS

A small, test-driven **Point of Sale** app for a café, built incrementally across labs.  

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



