// 代码2-24 / 代码2-25 / 代码2-26  税额＝含税金额×税率÷（100＋税率），按分四舍五入
package com.example.pos.tax;

import com.example.pos.counterexample.HardcodedTaxSale;
import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxAdapterTest {
    @Test
    void bookExample4400At3PercentIs128() {
        // 正文：4400×3÷（100＋3）≈128.16分，按分四舍五入为128分，应付金额仍为4400分
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        assertEquals(3, new TaxAdapter().getRate());
        assertEquals(128L, new TaxAdapter().taxOf(4400L));
        assertEquals(128L, sale.taxCents(sale.amountDueCents()));
        assertEquals(4400L, sale.amountDueCents());
    }

    @Test
    void policyAdaptersOverrideGetRateOnly() {
        Sale sale = new Sale();
        sale.setTaxAdapter(new JinIIAdapter());
        assertEquals(249L, sale.taxCents(4400L));    // 4400×6÷106≈249.06
        sale.setTaxAdapter(new JinIIIAdapter());
        assertEquals(436L, sale.taxCents(4400L));    // 4400×11÷111≈436.04
        assertThrows(IllegalArgumentException.class, () -> sale.setTaxAdapter(null));
        assertThrows(IllegalArgumentException.class, () -> new TaxAdapter().taxOf(-1L));
    }

    @Test
    @SuppressWarnings("deprecation")
    void hardcodedFlagsMatchAdapterResults() {   // 代码2-24反例：3%/6%/11%
        HardcodedTaxSale legacy = new HardcodedTaxSale();
        assertEquals(128L, legacy.taxCents(4400L, 1));
        assertEquals(249L, legacy.taxCents(4400L, 2));
        assertEquals(436L, legacy.taxCents(4400L, 3));
        assertEquals(0L, legacy.taxCents(4400L, 9));
    }

    @Test
    void configRejectsOutOfRangeRate() {
        assertThrows(IllegalArgumentException.class, () -> TaxPolicyConfigHelper.update(101));
    }
}
