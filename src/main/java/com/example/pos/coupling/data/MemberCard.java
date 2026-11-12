// 代码2-10  向打印机传递数据值——会员对象（正文未给出源码，配套代码补充）
package com.example.pos.coupling.data;

public class MemberCard {
    private final String id;
    private final long credits;

    public MemberCard(String id, long credits) {
        this.id = id;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public long getCredits() {
        return credits;
    }
}
