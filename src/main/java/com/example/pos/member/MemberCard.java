// 代码2-23  会员打折代码的多态实现（重构后）——抽象类MemberCard
package com.example.pos.member;

/** 重构前的 switch 写法见 counterexample.polymorphism.MemberCard（代码2-22）。 */
public abstract class MemberCard {
    // 返回百分制折扣比例，如98表示9.8折
    public abstract int discountPercent();
}
