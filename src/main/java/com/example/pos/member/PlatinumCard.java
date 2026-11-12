package com.example.pos.member;

/**
 * 铂金卡会员：享受 9.5 折（95%）。
 */
public class PlatinumCard extends MemberCard {
    @Override
    public int discountPercent() {
        return 95;
    }
}
