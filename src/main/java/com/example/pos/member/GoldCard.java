package com.example.pos.member;

/**
 * 金卡会员：享受 9.6 折（96%）。
 */
public class GoldCard extends MemberCard {
    @Override
    public int discountPercent() {
        return 96;
    }
}
