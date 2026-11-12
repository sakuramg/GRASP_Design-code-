package com.example.pos.counterexample;

/**
 * 代码2-21：按卡种分支的折扣计算（反例，2.5.1）。
 * 正例见 com.example.pos.member.MemberCard 与 com.example.pos.sale.Sale#finalPriceCents（代码2-22）。
 */
@Deprecated
public class BranchingDiscountSale {

    public enum CardType { NORMAL, SILVER, GOLD, PLATINUM }

    public record LegacyCard(CardType type) { }

    public long finalPriceCents(LegacyCard card, long totalCents) {
        if (card.type() == CardType.SILVER) {
            return totalCents * 98 / 100;
        } else if (card.type() == CardType.GOLD) {
            return totalCents * 96 / 100;
        } else if (card.type() == CardType.PLATINUM) {
            return totalCents * 95 / 100;
        }
        return totalCents;                    // 普通卡按原价
    }
}
