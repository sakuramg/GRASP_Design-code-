package com.example.pos.member;

/**
 * 代码2-14：包级私有的积分账本，包外代码在编译期不可见。
 */
class PointsLedger {

    private long points;

    void add(long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("积分增量不能为负");
        }
        points += delta;
    }

    long points() {
        return points;
    }
}
