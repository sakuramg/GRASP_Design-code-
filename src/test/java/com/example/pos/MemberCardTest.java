package com.example.pos;

import com.example.pos.counterexample.BranchingDiscountSale;
import com.example.pos.member.GoldCard;
import com.example.pos.member.MemberCardFactory;
import com.example.pos.member.MemberService;
import com.example.pos.member.NormalCard;
import com.example.pos.member.PlatinumCard;
import com.example.pos.member.SilverCard;
import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 代码2-21、2-22（2.5.1）：会员折扣的多态实现（与第三章规则统一）。
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
    @DisplayName("代码2-21反例的分支折扣与多态实现结果一致")
    @SuppressWarnings("deprecation")
    void branchingCounterexampleMatchesPolymorphism() {
        BranchingDiscountSale legacy = new BranchingDiscountSale();
        assertEquals(4900L, legacy.finalPriceCents(new BranchingDiscountSale.LegacyCard(BranchingDiscountSale.CardType.NORMAL), 4900L));
        assertEquals(4802L, legacy.finalPriceCents(new BranchingDiscountSale.LegacyCard(BranchingDiscountSale.CardType.SILVER), 4900L));
        assertEquals(4704L, legacy.finalPriceCents(new BranchingDiscountSale.LegacyCard(BranchingDiscountSale.CardType.GOLD), 4900L));
        assertEquals(4655L, legacy.finalPriceCents(new BranchingDiscountSale.LegacyCard(BranchingDiscountSale.CardType.PLATINUM), 4900L));
        assertEquals(100, new NormalCard().discountPercent());
        assertEquals(98, new SilverCard().discountPercent());
        assertEquals(96, new GoldCard().discountPercent());
        assertEquals(95, new PlatinumCard().discountPercent());
    }

    @Test
    @DisplayName("代码2-14：会员服务先授权再委托包内对象")
    void memberServiceChecksPolicy() {
        List<String> checks = new ArrayList<>();
        MemberService service = new MemberService((caller, memberId, purpose) -> {
            if ("guest".equals(caller)) {
                throw new SecurityException("未授权：" + purpose);
            }
            checks.add(purpose);
        });
        service.register("pos", "M001", new GoldCard());
        assertEquals(96, service.discountPercentOf("pos", "M001"));
        assertEquals(100, service.discountPercentOf("pos", "M999"), "未登记会员按原价");
        service.addPoints("pos", "M001", 4400L);
        assertEquals(44L, service.pointsOf("pos", "M001"), "每满100分记1积分");
        assertEquals(List.of("REGISTER", "DISCOUNT", "DISCOUNT", "POINTS", "POINTS"), checks);
        assertThrows(SecurityException.class, () -> service.discountPercentOf("guest", "M001"));
    }

    @Test
    @DisplayName("扩展新卡种只影响工厂，不触及Sale")
    void addingCardTypeTouchesOnlyFactory() {
        Sale sale = fixedReceipt();
        assertEquals(4802L, sale.finalPriceCents(MemberCardFactory.of("SILVER"), 4900L));
    }
}
