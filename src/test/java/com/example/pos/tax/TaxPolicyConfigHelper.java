// 代码2-25  测试辅助：调用 TaxPolicyConfig 的包内税率更新入口
package com.example.pos.tax;

/**
 * 测试辅助：把包内可见的税率更新入口暴露给测试。
 * 放在 tax 包内，才能调用包内可见的 updateRate，不必为了测试放宽产品代码的可见性。
 */
public final class TaxPolicyConfigHelper {

    private TaxPolicyConfigHelper() {
    }

    public static void update(int ratePercent) {
        TaxPolicyConfig.updateRate(ratePercent);
    }
}
