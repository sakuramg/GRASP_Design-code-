package com.example.pos.member;

/**
 * 会员卡装配工厂（2.5.1 多态实现）。
 * 统一对齐第三章等级体系：普通原价（100）、银卡98折、金卡96折、铂金95折。
 */
public final class MemberCardFactory {

    private MemberCardFactory() {
    }

    public static MemberCard of(String cardType) {
        return switch (cardType) {
            case "NORMAL" -> new NormalCard();
            case "SILVER" -> new SilverCard();
            case "GOLD" -> new GoldCard();
            case "PLATINUM" -> new PlatinumCard();
            default -> throw new IllegalArgumentException("未知卡种：" + cardType);
        };
    }
}
