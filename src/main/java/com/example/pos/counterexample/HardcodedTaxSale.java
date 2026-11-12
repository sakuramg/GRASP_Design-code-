// 代码2-24  用标志位选择硬编码税率（反例）
package com.example.pos.counterexample;

/**
 * 反例，仅供对照。正例见 com.example.pos.tax.TaxAdapter（代码2-25）与 Sale#taxCents（代码2-26）。
 * 3%、6%、11% 为教学税率；实际计税还需核实税种、纳税人资格、商品类别、生效日期、计税基数与法定舍入要求。
 */
@Deprecated
public class HardcodedTaxSale {
    public long taxCents(long grossCents, int rateFlag) {
        if (rateFlag == 1) {
            return includedTax(grossCents, 3);     // 小微企业所得税率
        } else if (rateFlag == 2) {
            return includedTax(grossCents, 6);     // 扶持期一般纳税人增值税率
        } else if (rateFlag == 3) {
            return includedTax(grossCents, 11);    // 一般纳税人增值税率
        }
        return 0L;
    }

    private long includedTax(long grossCents, int rate) {
        long divisor = 100L + rate;
        return (grossCents * rate * 2 + divisor) / (divisor * 2);   // 按分四舍五入
    }
}
