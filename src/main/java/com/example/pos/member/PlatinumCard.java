// 代码2-23  会员打折代码的多态实现（重构后）——PlatinumCard
package com.example.pos.member;

public class PlatinumCard extends MemberCard {
    @Override
    public int discountPercent() { return 95; }
}
