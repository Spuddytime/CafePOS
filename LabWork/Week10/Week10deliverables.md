# Week 10 — Layered Architecture, MVC, Components & Connectors

This week we stepped back from individual design patterns and applied a full **architectural perspective** to the Café POS project.  
Instead of adding new features, we reorganized the system into clean layers, introduced MVC in the Presentation layer, and added lightweight components & connectors via an EventBus.

---

##  Part A — Layered Architecture

We introduced a **4-layer architecture**:

### What was done

#### ✔ Domain Layer (Pure Business Logic)
- Moved `Order`, `LineItem`, `Money`, etc. into `com.cafepos.domain`.
- Added `OrderRepository` interface (stable core boundary).

#### ✔ Application Layer (Use Cases)
- Created `CheckoutService` that orchestrates pricing + domain.
- Added `ReceiptFormatter` to format receipts (no I/O).

#### ✔ Infrastructure Layer (Adapters)
- Added `InMemoryOrderRepository` as a simple persistence adapter.
- Centralized object creation in `Wiring` (the Composition Root).

#### ✔ Presentation Layer (UI)
- Added `OrderController` (translates UI intentions → application calls).
- Added `ConsoleView` for printing only.

### Why this matters
- Clear boundaries prevent “UI logic leaking into business code”.
- Domain stays reusable and framework-free.
- Makes the system easy to test and evolve.

---

##  Part B — MVC (Console)

Implemented a small **Model–View–Controller** flow:

- **Model** → domain objects (`Order`, `LineItem`, `Money`)
- **View** → console printing (`ConsoleView`)
- **Controller** → coordinates use cases (`OrderController`)

### Demo (`Week10Demo_MVC`)
Creates an order, adds items, gets receipt text from the controller, and prints via the view.

---

##  Part C — Components & Connectors (EventBus)

Introduced a lightweight **EventBus** as a connector so components communicate without tight coupling.

### What we added

#### ✔ Event types
- `OrderCreated`
- `OrderPaid`

#### ✔ EventBus
- `on(eventType, handler)`
- `emit(event)`

#### ✔ Event wiring demo
UI subscribes to events and reacts to domain changes:


This demonstrates a simple **ports & adapters** (hexagonal-ish) architecture:
- Controllers/Services publish events (output ports)
- UI listens to them (input adapters)

---

## Part D — Architecture Reflection

### Why a Layered Monolith (for now)?

A layered monolith keeps development simple and fast:
- One deployable unit
- No network calls or distributed failures
- Easy debugging, refactoring, and testing
- Perfect for a college project with rapid weekly changes

The architecture still has clear **seams**, meaning potential future microservices:
- Payments
- Notifications
- Reporting / Analytics
- Order State Machine
- Printers / External devices

These seams exist because the Architecture already uses:
- Interfaces (`OrderRepository`)
- Application services (`CheckoutService`)
- Infrastructure adapters (`InMemoryOrderRepository`)
- Event connectors (`EventBus`)

### If partitioned in the future:

We would introduce:
- **REST APIs** between services
- **Event-driven messaging** (Kafka/RabbitMQ) for order lifecycle events
- **Database-per-service**
- **Payment service / Notification service** extracted from the monolith

The layered monolith makes such refactoring safe and gradual.

---

###  Diagram Below for week 10 

![Week10 Puml Diagram.png](Week10%20Puml%20Diagram.png)

