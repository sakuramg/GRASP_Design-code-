// 代码2-12  两个类共用全局单例的数据访问对象
package com.example.pos.coupling.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class ItemDaoTest {
    @Test
    void bothClassesShareTheSameSingletonAndItem() {
        assertSame(ItemDao.getDAO(), ItemDao.getDAO());
        SaleLineItem line = new SaleLineItem();
        DaySumItem day = new DaySumItem();
        line.loadItem("A");
        day.loadItem("A");
        assertSame(line.getItem(), day.getItem());   // 一方改动，另一方立即可见
    }
}
