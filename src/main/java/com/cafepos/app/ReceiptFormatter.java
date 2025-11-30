package com.cafepos.app;

import com.cafepos.common.*;
import com.cafepos.domain.LineItem;
import com.cafepos.pricing.PricingService;

import java.util.List;

/** Application-layer formatter: formats an Order (id + line items) into a receipt string. */
public final class ReceiptFormatter {

    public String format(long orderId, List<LineItem> items, PricingService.PricingResult pr, int taxPercent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append("\n");
        for (LineItem li : items) {
            sb.append(" - ").append(li.product().name())
                    .append(" x").append(li.quantity())
                    .append(" = ").append(li.lineTotal()).append("\n");
        }

        sb.append("Subtotal: ").append(pr.subtotal()).append("\n");
        if (pr.discount().asBigDecimal().signum() > 0) {
            sb.append("Discount: -").append(pr.discount()).append("\n");
        }

        sb.append("Tax (").append(taxPercent).append("%): ").append(pr.tax()).append("\n");
        sb.append("Total: ").append(pr.total());
        return sb.toString();
    }
}
