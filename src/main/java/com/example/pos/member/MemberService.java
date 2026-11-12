package com.example.pos.member;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码2-14：会员模块的受控入口与包内协作（关键代码，2.3.3）。
 * 对外入口：先做运行期授权，再委托包内对象。
 */
public class MemberService {

    private final Map<String, MemberCard> cards = new HashMap<>();
    private final Map<String, PointsLedger> ledgers = new HashMap<>();
    private final AccessPolicy policy;

    public MemberService(AccessPolicy policy) {
        this.policy = policy;
    }

    public void register(String caller, String memberId, MemberCard card) {
        policy.check(caller, memberId, "REGISTER");
        cards.put(memberId, card);
    }

    public int discountPercentOf(String caller, String memberId) {
        policy.check(caller, memberId, "DISCOUNT");
        MemberCard card = cards.get(memberId);
        return card == null ? 100 : card.discountPercent();
    }

    public void addPoints(String caller, String memberId, long paidCents) {
        policy.check(caller, memberId, "POINTS");
        // 演示规则：每满100分记1积分
        ledgers.computeIfAbsent(memberId, id -> new PointsLedger()).add(paidCents / 100);
    }

    /** 配套代码补充的只读查询，便于测试核对积分 */
    public long pointsOf(String caller, String memberId) {
        policy.check(caller, memberId, "POINTS");
        PointsLedger ledger = ledgers.get(memberId);
        return ledger == null ? 0L : ledger.points();
    }
}
