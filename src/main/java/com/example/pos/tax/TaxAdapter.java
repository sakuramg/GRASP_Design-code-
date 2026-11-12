// 代码2-25  税费计算的中间类（正例）
package com.example.pos.tax;

/**
 * 具体类：税率由 getRate() 从外部配置动态读取；政策变化时派生新的实现类重写 getRate()，
 * 如 JinIIAdapter、JinIIIAdapter（图2-13）。税额只在小票上拆分展示，不加到应付金额上。
 */
public class TaxAdapter {
    public int getRate() {
        return TaxPolicyConfig.currentRate();      // 获取当前最新税率
    }

    public long taxOf(long grossCents) {
        if (grossCents < 0) {
            throw new IllegalArgumentException("含税金额不能为负数");
        }
        int rate = getRate();
        long divisor = 100L + rate;
        // 税额＝含税金额×税率÷（100＋税率），按分四舍五入
        return (grossCents * rate * 2 + divisor) / (divisor * 2);
    }
}
