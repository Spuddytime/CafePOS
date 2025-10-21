## Week 6

- **Step 1 – Characterize behavior**
    - Added `Week6CharacterizationTests` to lock the current output of `OrderManagerGod`.
    - Tests verify subtotal, discount line (when present), tax label/amount, and total.

- **Step 2 – Identify smells (comments only)**
    - Annotated `OrderManagerGod.process(...)` with comments for:
        - God Class / Long Method
        - Primitive Obsession (codes/percent as raw strings/ints)
        - Duplicated BigDecimal math
        - Feature Envy(Class doing work for another class) / Shotgun Surgery risk (Small change forces lots of edits elsewhere)
        - Global/Static state (`TAX_PERCENT`, `LAST_DISCOUNT_CODE`)
    - **No behavior changes**.

- **Step 3 – Add clean building blocks (not wired yet)**
    - Pricing primitives:
        - `com.cafepos.pricing.DiscountPolicy`
        - `NoDiscount`, `LoyaltyPercentDiscount`, `FixedCouponDiscount`
        - `com.cafepos.pricing.TaxPolicy` 
        - `FixedRateTaxPolicy` (with `percent()` getter)
    - Pipeline & formatting:
        - `PricingService` (+ `PricingResult` record)
        - `ReceiptPrinter` (matches baseline receipt format exactly)
    - These classes compile and are ready to be injected later; **outputs unchanged**.
    - Commit sequence used:
        - `test: add characterization tests for OrderManagerGod`
        - `docs(smells): annotate OrderManagerGod with smell comments (no code changes)`
        - `refactor(discount): add DiscountPolicy + implementations (no behavior change)`
        - `refactor(tax): add TaxPolicy + FixedRateTaxPolicy (no behavior change)`
        - `refactor(pricing): add PricingService pipeline`
        - `refactor(io): add ReceiptPrinter (format matches baseline)`

**Why this matters**
- Creates safe “seams” so later refactors won’t touch the big method.
- Reduces risk of **Shotgun Surgery** by isolating tax/discount rules.
- Sets up **OCP/SRP**: new discounts/taxes are new classes, not edits.

---

