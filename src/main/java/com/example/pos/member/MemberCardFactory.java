package com.example.pos.member;

/**
 * 装配入口：新增卡种时只改这里（2.5.1 案例）。
 */
public final class MemberCardFactory {

    private MemberCardFactory() {
    }

    public static MemberCard of(String cardType) {
        return switch (cardType) {
            case "NORMAL" -> new NormalCard();
            case "SILVER" -> new SilverCard();
            case "PLATINUM" -> new PlatinumCard();
            case "GOLD" -> new GoldCard();
            case "DIAMOND" -> new DiamondCard();
            default -> throw new IllegalArgumentException("未知卡种：" + cardType);
        };
    }
}
