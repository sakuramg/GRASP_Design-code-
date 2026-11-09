package com.example.pos.tax;

/**
 * 代码清单2-23：税费计算的中间对象（正例，2.5.2）。
 * 税金中间对象：税率读取与税金计算的唯一归属地。
 * 中间对象可以是具体类、抽象类或接口——在仅有一种计税通道时，具体类最为简洁。
 */
public class TaxAdapter {

    /** 动态获取当前生效的税率百分比，外部政策调整只影响配置源与本类 */
    public int currentRatePercent() {
        return TaxPolicyConfig.currentRate();
    }

    /** 根据整单金额计算税金 */
    public long taxOf(long totalCents) {
        return totalCents * currentRatePercent() / 100;
    }
}
