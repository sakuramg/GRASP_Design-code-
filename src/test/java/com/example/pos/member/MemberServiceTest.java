// 代码2-16  会员模块的继承链、包内协作与统一入口
package com.example.pos.member;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    @Test
    void serviceChecksPolicyBeforeDelegating() {
        List<String> purposes = new ArrayList<>();
        MemberService service = new MemberService((caller, id, purpose) -> {
            if ("guest".equals(caller)) {
                throw new SecurityException(purpose);
            }
            purposes.add(purpose);
        });
        service.register("pos", "M001");
        service.addPoints("pos", "M001", 4400L);
        assertEquals(44L, service.find("M001").points);           // 每100分记1分
        assertThrows(IllegalStateException.class, () -> service.consume("pos", "M001", 1L)); // 储值余额不足
        assertThrows(SecurityException.class, () -> service.addPoints("guest", "M001", 100L));
        assertEquals(List.of("REGISTER", "POINTS", "CONSUME"), purposes);
    }

    @Test
    void goldMemberDoublesPointsAndPolicyUpgrades() {
        Member gold = new GoldMember("G1");
        gold.addPoints(4400L);
        assertEquals(88L, gold.points);
        Member normal = new Member("N1");
        assertSame(normal, new MemberLevelPolicy().upgrade(normal));
        normal.points = 10000;
        assertInstanceOf(GoldMember.class, new MemberLevelPolicy().upgrade(normal));
    }
}
