package com.example.pos;

import com.example.pos.member.MemberCardFactory;
import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 2.5.1：会员折扣的多态实现（与第三章规则统一）。
 * 同一笔 4900 分订单，各等级卡折后金额各不相同，Sale 代码完全保持不变。
 */
class MemberCardTest {

    private static Sale fixedReceipt() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        return sale;
    }

    @Test
    @DisplayName("四种卡对同一笔4900分订单的折后金额（统一对齐第三章数值）")
    void discountDiffersByCardType() {
        Sale sale = fixedReceipt();
        long total = sale.totalCents(); // 4900

        assertEquals(4900L, sale.finalPriceCents(MemberCardFactory.of("NORMAL"), total));   // 100% 原价
        assertEquals(4802L, sale.finalPriceCents(MemberCardFactory.of("SILVER"), total));   // 98折: 4900 * 98 / 100 = 4802
        assertEquals(4704L, sale.finalPriceCents(MemberCardFactory.of("GOLD"), total));     // 96折: 4900 * 96 / 100 = 4704
        assertEquals(4655L, sale.finalPriceCents(MemberCardFactory.of("PLATINUM"), total)); // 95折: 4900 * 95 / 100 = 4655
    }

    @Test
    @DisplayName("扩展新卡种只影响工厂，不触及Sale")
    void addingCardTypeTouchesOnlyFactory() {
        Sale sale = fixedReceipt();
        assertEquals(4802L, sale.finalPriceCents(MemberCardFactory.of("SILVER"), 4900L));
    }
}
