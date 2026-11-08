package com.example.pos.tax;

/**
 * 税金中间对象：税率与算法的唯一归属地（间接，2.5.2）。
 * 中间类可以是抽象类、接口或具体类——此处用具体类。
 */
public class TaxAdapter {

    /** 动态取得当前生效税率，政策变化只改这一处。 */
    public int currentRatePercent() {
        return TaxPolicyConfig.currentRate();
    }

    /** 计税：所需信息由本类直接提供。 */
    public long taxOf(long totalCents) {
        return totalCents * currentRatePercent() / 100;
    }
}
