package com.example.pos.member;

/**
 * 会员卡抽象：折扣契约的稳定定义（多态，2.5.1）。
 * discountPercent() 返回百分数：95 表示九五折。
 */
public abstract class MemberCard {

    public abstract int discountPercent();
}
