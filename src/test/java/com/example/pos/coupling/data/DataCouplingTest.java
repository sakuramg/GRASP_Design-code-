// 代码2-10  向打印机传递数据值
package com.example.pos.coupling.data;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataCouplingTest {
    @Test
    void printerReceivesOnlyValues() {
        AtomicReference<String> out = new AtomicReference<>();
        new Sale(List.of(), new MemberCard("M001", 44L), out::set).print();
        assertEquals("会员号：M001，积分：44", out.get());
    }
}
