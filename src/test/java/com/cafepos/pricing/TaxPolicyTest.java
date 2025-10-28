package com.cafepos.pricing;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class TaxPolicyTest {
    @Test void fixed_rate_10_percent_of_7_41_is_0_74() {
        assertEquals(Money.of(0.74), new FixedRateTaxPolicy(10).taxOn(Money.of(7.41)));
    }
}
