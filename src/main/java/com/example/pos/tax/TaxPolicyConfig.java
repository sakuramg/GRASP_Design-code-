package com.example.pos.tax;

/**
 * 税率来源（教学简化）：真实系统由配置下发，不在代码里写死。
 */
public final class TaxPolicyConfig {

    private static volatile int currentRate = 3;   // 百分数

    private TaxPolicyConfig() {
    }

    public static int currentRate() {
        return currentRate;
    }

    /** 仅供教学演示：真实系统通过配置分发与生效日期管理。 */
    static void updateRate(int ratePercent) {
        if (ratePercent < 0 || ratePercent > 100) {
            throw new IllegalArgumentException("税率越界：" + ratePercent);
        }
        currentRate = ratePercent;
    }
}
