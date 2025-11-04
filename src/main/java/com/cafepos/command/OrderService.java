package com.cafepos.command;

import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.Product;
import com.cafepos.factory.ProductFactory;
import com.cafepos.Payment.PaymentStrategy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Receiver: thin façade over your existing domain.
 * Commands call this; it calls Order, Factory, and PaymentStrategy.
 */
public final class OrderService {
    private final ProductFactory factory = new ProductFactory();
    private final Order order;

    public OrderService(Order order) {
        if (order == null) throw new IllegalArgumentException("order required");
        this.order = order;
    }

    /** Add an item built from a recipe string (e.g., "ESP+SHOT+OAT"). */
    public void addItem(String recipe, int qty) {
        Product p = factory.create(recipe);
        order.addItem(new LineItem(p, qty));
        System.out.println("[Service] Added " + p.name() + " x" + qty);
    }

    /**
     * Remove the last item.
     * If the Order exposes a remover, call that instead.
     * This fallback uses reflection to keep your Order unchanged.
     */
    @SuppressWarnings("unchecked")
    public void removeLastItem() {
        try {
            Field f = Order.class.getDeclaredField("items");
            f.setAccessible(true);
            List<LineItem> items = new ArrayList<>((List<LineItem>) f.get(order));
            if (items.isEmpty()) {
                System.out.println("[Service] No items to remove");
                return;
            }
            items.remove(items.size() - 1);
            f.set(order, items);
            System.out.println("[Service] Removed last item");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Consider adding Order.removeLastItem()", e);
        }
    }

    public Money totalWithTax(int percent) { return order.totalWithTax(percent); }

    /** Delegate payment to existing strategy; also prints a confirmation line. */
    public void pay(PaymentStrategy strategy, int taxPercent) {
        if (strategy == null) throw new IllegalArgumentException("strategy required");
        Money total = order.totalWithTax(taxPercent);
        strategy.pay(order);
        System.out.println("[Service] Payment processed for total " + total);
    }

    public Order order() { return order; }
}
