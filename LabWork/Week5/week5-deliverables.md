# Week 5 — Decorator + Factory Café POS (Deliverables)

This week extends the Café POS by adding **Decorator** (to stack add-ons onto a base drink) and **Factory** (to build drinks from short recipe strings). Core domain from previous weeks (**Money**, **Product**, **SimpleProduct**, **Order**, **LineItem**, payments, observers) stays intact; we only add composition around products and a central creator.

---

## What Was Added in Week 5

1. **Pricing Interface**
    - `Priced` → defines `Money price();`
    - Implemented by `SimpleProduct` (returns `basePrice()`) and **all** decorators.

2. **Decorator Base + Concrete Add-ons**
    - `ProductDecorator` (abstract) wraps a `Product`; delegates `id()` and `basePrice()`.
    - Concrete decorators (each implements `Priced` and appends to `name()`):
        - `ExtraShot` **(+€0.80)** → `" + Extra Shot"`
        - `OatMilk` **(+€0.50)** → `" + Oat Milk"`
        - `Syrup` **(+€0.40)** → `" + Syrup"`
        - `SizeLarge` **(+€0.70)** → `" (Large)"`
    - Each computes `price()` as: wrapped `price()` (or `basePrice()`) **plus** its surcharge via `Money.add()`.

3. **Order Integration**
   --LineItem.lineTotal()` now uses decorated price when available:
      ```java
      Money unit = (product instanceof Priced p) ? p.price() : product.basePrice();
      return unit.multiply(quantity);
      ```

4. **Product Factory (recipes)**
    - `ProductFactory.create(String recipe)` parses tokens like `ESP+SHOT+OAT+L`.
    - Bases: `ESP` (2.50), `LAT` (3.20), `CAP` (3.00).
    - Add-ons: `SHOT`, `OAT`, `SYP`, `L`.
    - Trims and uppercases tokens; fails fast on unknown tokens.

---

## Week5Demo (what it shows)

- Builds two drinks via `ProductFactory`: `ESP+SHOT+OAT` and `LAT+L`.
- Adds them to an `Order`, prints each line (`name`, `qty`, `lineTotal`), plus subtotal, tax, total.
- Confirms decorators only affect **names** and **unit prices**; Week 2 math remains unchanged.

---

## JUnit Tests


