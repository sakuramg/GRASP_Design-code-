package com.example.pos.counterexample;

/**
 * 代码清单2-22：用标志位选择硬编码税率（反例，2.5.2）。
 * 正例见 com.example.pos.tax.TaxAdapter 与 com.example.pos.sale.Sale#taxCents(long)。
 */
@Deprecated
public class HardcodedTaxSale {

    public long taxCents(long totalCents, int rateFlag) {
        if (rateFlag == 1) {
            return totalCents * 3 / 100;      // 3% 税率
        } else if (rateFlag == 2) {
            return totalCents * 6 / 100;      // 6% 税率
        } else if (rateFlag == 3) {
            return totalCents * 11 / 100;     // 11% 税率
        }
        return 0L;
    }
}
