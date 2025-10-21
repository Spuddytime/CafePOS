package com.cafepos.pricing;

/** Builds the receipt string exactly like the smelly code. */
public final class ReceiptPrinter {
    public String format(String recipe, int qty, PricingService.PricingResult pr, int taxPercent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        sb.append("Subtotal: ").append(pr.subtotal()).append("\n");
        if (pr.discount().asBigDecimal().signum() > 0) {
            sb.append("Discount: -").append(pr.discount()).append("\n");
        }
        sb.append("Tax (").append(taxPercent).append("%): ").append(pr.tax()).append("\n");
        sb.append("Total: ").append(pr.total());
        return sb.toString();
    }
}
