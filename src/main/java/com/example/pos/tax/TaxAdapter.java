package com.example.pos.tax;

/**
 * 代码2-24：税费计算的中间对象（正例，2.5.2）。
 * 税率读取与税额计算的唯一归属地；税额只在小票上拆分展示，不加到应付金额上。
 */
public class TaxAdapter {

    public int currentRatePercent() {
        return TaxPolicyConfig.currentRate();      // 税率来自外部配置源
    }

    public long taxOf(long grossCents) {
        if (grossCents < 0) {
            throw new IllegalArgumentException("含税金额不能为负数");
        }
        int rate = currentRatePercent();
        long divisor = 100L + rate;
        // 税额＝含税金额×税率÷（100＋税率），按分四舍五入
        return (grossCents * rate * 2 + divisor) / (divisor * 2);
    }
}
