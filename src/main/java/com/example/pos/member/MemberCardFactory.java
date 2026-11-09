package com.example.pos.member;

/**
 * 会员卡装配工厂（2.5.1案例2-5与2.7节第9题验证）。
 * 基础四类卡种见代码清单2-21；SILVER（92折银卡）为课后习题第9题的扩展示例，验证新增卡种时 Sale 代码无需修改。
 */
public final class MemberCardFactory {

    private MemberCardFactory() {
    }

    public static MemberCard of(String cardType) {
        return switch (cardType) {
            case "NORMAL" -> new NormalCard();
            case "PLATINUM" -> new PlatinumCard();
            case "GOLD" -> new GoldCard();
            case "DIAMOND" -> new DiamondCard();
            case "SILVER" -> new SilverCard();   // 2.7节第9题扩展验证：新增92折银卡
            default -> throw new IllegalArgumentException("未知卡种：" + cardType);
        };
    }
}
