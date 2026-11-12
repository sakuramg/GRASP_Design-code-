package com.example.pos.tax;

/**
 * 代码清单2-23：税费计算的中间对象（正例，2.5.2）。
 * 税金中间对象：税率读取与税金计算的唯一归属地。
 * 中间对象可以是具体类、抽象类或接口。
 */
public class TaxAdapter {

    /** 动态获取当前生效的税率百分比，外部政策调整只影响配置源与本类 */
    public int currentRatePercent() {
        return TaxPolicyConfig.currentRate();
    }

    /**
     * 根据优惠后含税应付金额（grossCents）反向拆分出票面展示税额：
     * 公式：grossCents * rate / (100 + rate)
     */
    public long taxOf(long grossCents) {
        int rate = currentRatePercent();
        return Math.round((double) grossCents * rate / (100.0 + rate));
    }
}
