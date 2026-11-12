package com.example.pos;

import com.example.pos.counterexample.HardcodedTaxSale;
import com.example.pos.counterexample.LegacyPriceController;
import com.example.pos.sale.Sale;
import com.example.pos.tax.TaxAdapter;
import com.example.pos.tax.TaxPolicyConfigHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 代码2-23、2-24、2-25（2.5.2）：税费计算的中间对象。税率来源变化不影响调用方。
 */
class TaxAdapterTest {

    @Test
    @DisplayName("从优惠后含税金额中反向拆分税额")
    void taxFollowsCurrentRate() {
        // 正文2.5.2：4400×3÷（100＋3）≈128.16分，按分四舍五入为128分
        assertEquals(128L, new TaxAdapter().taxOf(4400L));
        assertEquals(128L, new Sale().taxCents(4400L), "代码2-25：Sale委托TaxAdapter");
    }

    @Test
    @DisplayName("负的含税金额被TaxAdapter拒绝")
    void negativeGrossRejected() {
        assertThrows(IllegalArgumentException.class, () -> new TaxAdapter().taxOf(-1L));
    }

    @Test
    @DisplayName("Sale可替换计税适配器，空适配器被拒绝")
    void saleTaxAdapterReplaceable() {
        Sale sale = new Sale();
        sale.setTaxAdapter(new TaxAdapter() {
            @Override
            public int currentRatePercent() {
                return 6;
            }
        });
        assertEquals(249L, sale.taxCents(4400L));   // 4400×6÷106≈249.06
        assertThrows(NullPointerException.class, () -> sale.setTaxAdapter(null));
    }

    @Test
    @DisplayName("代码2-23反例与正例在同一税率下结果一致")
    @SuppressWarnings("deprecation")
    void hardcodedTaxMatchesAdapterFormula() {
        HardcodedTaxSale legacy = new HardcodedTaxSale();
        assertEquals(128L, legacy.taxCents(4400L, 1));
        assertEquals(249L, legacy.taxCents(4400L, 2));
        assertEquals(0L, legacy.taxCents(4400L, 9));
        assertEquals(128L, new LegacyPriceController().calculateTaxByFlag(legacy, 4400L, 1));
    }

    @Test
    @DisplayName("税率越界在配置入口被拒绝")
    void rateOutOfRangeRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> TaxPolicyConfigHelper.update(101));
    }
}
