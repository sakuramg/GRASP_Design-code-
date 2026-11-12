package com.example.pos.counterexample;

/**
 * 代码2-23：用标志位选择硬编码税率（反例，2.5.2）；代码2-10中的同名类为其内联写法，计算结果相同。
 * 正例见 com.example.pos.tax.TaxAdapter（代码2-24）与 com.example.pos.sale.Sale#taxCents(long)（代码2-25）。
 */
@Deprecated
public class HardcodedTaxSale {

    public long taxCents(long grossCents, int rateFlag) {
        if (rateFlag == 1) {
            return includedTax(grossCents, 3);     // 3%、6%均为教学税率
        } else if (rateFlag == 2) {
            return includedTax(grossCents, 6);
        }
        return 0L;
    }

    private long includedTax(long grossCents, int rate) {
        long divisor = 100L + rate;
        return (grossCents * rate * 2 + divisor) / (divisor * 2);   // 按分四舍五入
    }
}
