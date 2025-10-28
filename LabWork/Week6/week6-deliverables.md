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

### Step 4 – Replace `OrderManagerGod` with a tiny orchestrator
- Added **`com.cafepos.checkout.CheckoutService`**.
- Responsibilities:
    - Build product via `ProductFactory`.
    - Get unit price (decorated `Priced.price()` if present; else `basePrice()`).
    - Compute subtotal and delegate to `PricingService` for discount→tax→total.
    - Format the receipt using `ReceiptPrinter` so the text matches the baseline.
- Public API used in demo/tests:
    - `String checkout(String recipe, int qty)`
- Commit:
    - `refactor(orchestrator): add CheckoutService (no behavior change yet)`

### Step 5 – Prove the clean path matches the smelly path (tests)
- **Unit tests (new):**
    - `DiscountPolicyTest` – verifies `NoDiscount`, `LoyaltyPercentDiscount(5)`, `FixedCouponDiscount(1.00)`.
    - `TaxPolicyTest` – verifies `FixedRateTaxPolicy(10)` tax math.
    - `PricingServiceTest` – verifies pipeline math from subtotal→discount→tax→total (e.g., 7.80→0.39→0.74→8.15).
- **Parity test (optional but helpful):**
    - `ParityTest` – builds receipts for the same input via:
        - old: `OrderManagerGod.process(...)`
        - new: `CheckoutService.checkout(...)`
        - Asserts the strings are **identical**.
- Commit(s):
    - `test: add DiscountPolicyTest`
    - `test: add TaxPolicyTest`
    - `test: add PricingServiceTest`
    - `test(optional): add ParityTest for receipt equality`

### Step 6 – CLI parity demo (30-second proof)
- **`com.cafepos.demo.Week6Demo`** (or **MidTermDemo**) prints both receipts and a `Match: true` line.

