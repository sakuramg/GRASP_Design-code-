// 代码2-20 / 代码2-21  按键与功能分离、键位装配
package com.example.pos.board;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleBoardTest {
    @Test
    void configureAndReassignKeys() {
        SaleBoard board = new SaleBoard();
        board.addButton(new Button("M"));
        board.addButton(new Button("Y"));
        board.addButton(new Button("P"));
        board.configure();
        assertDoesNotThrow(() -> board.getButton("M").onClick());
        AtomicInteger count = new AtomicInteger();
        board.getButton("M").setAction(count::incrementAndGet);   // 换功能不改Button
        board.getButton("M").onClick();
        assertEquals(1, count.get());
        assertThrows(IllegalArgumentException.class, () -> board.getButton("Z"));
        assertDoesNotThrow(() -> new Button("X").onClick());       // 未注入功能时不做事
    }
}
