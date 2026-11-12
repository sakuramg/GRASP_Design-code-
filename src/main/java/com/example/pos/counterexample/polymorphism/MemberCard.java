// 代码2-22  按卡种分支的会员打折代码（重构前）——MemberCard部分
package com.example.pos.counterexample.polymorphism;

/** 重构前写法，仅供对照。重构后见 com.example.pos.member.MemberCard 及其子类（代码2-23）。 */
@Deprecated
public class MemberCard {
    private final int cardType;               // 1银卡，2金卡，3铂金卡，其他为普通卡

    public MemberCard(int cardType) {
        this.cardType = cardType;
    }

    public int discountPercent() {
        switch (cardType) {
            case 1: return 98;
            case 2: return 96;
            case 3: return 95;
            default: return 100;
        }
    }
}
