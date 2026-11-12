// 代码2-22  按卡种分支的会员打折代码（重构前）——业务端Sale部分
package com.example.pos.counterexample.polymorphism;

/**
 * 重构前的业务端代码。重构后同一方法原样保留在 com.example.pos.sale.Sale#finalPriceCents，
 * 只是参数类型换成抽象类 com.example.pos.member.MemberCard（Java中两个同名类须分属不同包）。
 */
@Deprecated
public class Sale {
    // 业务端代码：只调用折扣方法，重构时保持不变
    public long finalPriceCents(MemberCard card, long totalCents) {
        return totalCents * card.discountPercent() / 100;
    }
}
