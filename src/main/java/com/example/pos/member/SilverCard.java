package com.example.pos.member;

/**
 * 银卡会员：享受 9.8 折（98%）。
 */
public class SilverCard extends MemberCard {
    @Override
    public int discountPercent() {
        return 98;
    }
}
