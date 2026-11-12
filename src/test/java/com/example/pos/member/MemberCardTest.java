// 代码2-22 / 代码2-23  会员折扣：普通100、银卡98、金卡96、铂金95；4900分基数分别为4900/4802/4704/4655分
package com.example.pos.member;

import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberCardTest {
    private static long base() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        return sale.totalCents();   // 4900，不叠加满减
    }

    @Test
    void polymorphicDiscounts() {
        assertEquals(100, new NormalCard().discountPercent());
        assertEquals(98, new SilverCard().discountPercent());
        assertEquals(96, new GoldCard().discountPercent());
        assertEquals(95, new PlatinumCard().discountPercent());
        Sale sale = new Sale();
        long total = base();
        assertEquals(4900L, total);
        assertEquals(4900L, sale.finalPriceCents(new NormalCard(), total));
        assertEquals(4802L, sale.finalPriceCents(new SilverCard(), total));
        assertEquals(4704L, sale.finalPriceCents(new GoldCard(), total));
        assertEquals(4655L, sale.finalPriceCents(new PlatinumCard(), total));
    }

    @Test
    @SuppressWarnings("deprecation")
    void switchVersionGivesSameResults() {   // 代码2-22重构前
        var sale = new com.example.pos.counterexample.polymorphism.Sale();
        int[] types = {0, 1, 2, 3};
        long[] expected = {4900L, 4802L, 4704L, 4655L};
        for (int i = 0; i < types.length; i++) {
            var card = new com.example.pos.counterexample.polymorphism.MemberCard(types[i]);
            assertEquals(expected[i], sale.finalPriceCents(card, 4900L));
        }
    }
}
