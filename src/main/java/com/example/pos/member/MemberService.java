// 代码2-16  会员模块的继承链、包内协作与统一入口（关键代码）
package com.example.pos.member;
import java.util.HashMap;
import java.util.Map;
// 普通会员：继承链的根，类本身不对包外公开
class Member {
    private final String memberId;
    protected long balanceCents;
    protected long points;
    Member(String memberId) { this.memberId = memberId; }
    public String getMemberId() { return memberId; }
    // 包级私有：包外模块不能直接调用
    void addPoints(long paidCents) { points += paidCents / 100; }
    void consume(long amountCents) {
        if (amountCents > balanceCents) {
            throw new IllegalStateException("储值余额不足");
        }
        balanceCents -= amountCents;
    }
}
class SilverMember extends Member {
    SilverMember(String memberId) { super(memberId); }
}
class GoldMember extends SilverMember {
    GoldMember(String memberId) { super(memberId); }
    // 演示规则：金卡积分加倍，直接访问父类protected成员
    @Override
    void addPoints(long paidCents) { points += paidCents / 100 * 2; }
}
// 同包协作类：使用default方法访问会员数据
class MemberLevelPolicy {
    Member upgrade(Member m) {
        return m.points >= 10000 ? new GoldMember(m.getMemberId()) : m;
    }
}
// 统一入口：由REST或RPC适配层导出
public class MemberService {
    private final Map<String, Member> members = new HashMap<>();
    private final AccessPolicy policy;
    public MemberService(AccessPolicy policy) { this.policy = policy; }
    public void register(String caller, String memberId) {
        policy.check(caller, memberId, "REGISTER");
        members.put(memberId, new Member(memberId));
    }
    public void addPoints(String caller, String memberId, long paidCents) {
        policy.check(caller, memberId, "POINTS");
        members.get(memberId).addPoints(paidCents);
    }
    public void consume(String caller, String memberId, long amountCents) {
        policy.check(caller, memberId, "CONSUME");
        members.get(memberId).consume(amountCents);
    }
    // 配套代码补充：包级私有查询，仅供同包测试核对状态，包外不可见
    Member find(String memberId) { return members.get(memberId); }
}
interface AccessPolicy {
    void check(String caller, String memberId, String purpose);
}
