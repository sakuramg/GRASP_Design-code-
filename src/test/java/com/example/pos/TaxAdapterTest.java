package com.example.pos;

import com.example.pos.tax.TaxAdapter;
import com.example.pos.tax.TaxPolicyConfigHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 2.5.2：税费计算的中间对象。税率来源变化不影响调用方。
 */
class TaxAdapterTest {

    @Test
    @DisplayName("从优惠后含税金额中反向拆分税额")
    void taxFollowsCurrentRate() {
        // 优惠后应付 4400 分，当前默认税率 3%：4400 * 3 / 103 ≈ 128 分
        long tax = new TaxAdapter().taxOf(4400L);
        long expected = Math.round(4400.0 * 3 / 103.0);
        assertEquals(expected, tax);
    }

    @Test
    @DisplayName("税率越界在配置入口被拒绝")
    void rateOutOfRangeRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> TaxPolicyConfigHelper.update(101));
    }
}
