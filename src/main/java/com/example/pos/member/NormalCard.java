package com.example.pos.member;

/**
 * 普通会员：享受原价（100%，不打折）。
 */
public class NormalCard extends MemberCard {
    @Override
    public int discountPercent() {
        return 100;
    }
}
