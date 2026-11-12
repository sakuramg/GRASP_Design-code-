package com.example.pos;

import com.example.pos.checkout.Printer;
import com.example.pos.counterexample.GodCheckoutController;
import com.example.pos.counterexample.HardcodedSettleButton;
import com.example.pos.counterexample.LegacyRegister;
import com.example.pos.counterexample.PriceController;
import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import com.example.pos.sale.SaleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 反例可运行性检查：反例与正文清单一致，并能演示其缺陷。
 */
@SuppressWarnings("deprecation")
class CounterexampleTest {

    @Test
    @DisplayName("代码2-11反例绕过校验写入负价，代码2-12正例拒绝")
    void contentCouplingBypassesValidation() {
        PriceController.Item exposed = new PriceController.Item("A", 1250L);
        new PriceController().adjustPrice(Map.of(exposed, -1L));
        assertEquals(-1L, exposed.unitPriceCents, "反例：负价被直接写入");

        Item item = new Item("A", 1250L);
        assertThrows(IllegalArgumentException.class,
                () -> new com.example.pos.coupling.PriceController().adjustPrice(Map.of(item, -1L)));
        assertEquals(1250L, item.getUnitPriceCents());
    }

    @Test
    @DisplayName("代码2-15反例按钮：4400分×96%后计税并关闭小票")
    void hardcodedButtonDoesEverything() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        AtomicLong tax = new AtomicLong(-1L);
        Printer printer = (s, t) -> tax.set(t);
        new HardcodedSettleButton(sale, printer, 96).actionPerformed(null);
        assertEquals(123L, tax.get(), "4224×3÷103≈123.03");
        assertEquals(SaleStatus.SETTLED, sale.getStatus());
    }

    @Test
    @DisplayName("代码2-16反例：Sale直接响应条码事件")
    void saleListensToBarcode() {
        com.example.pos.counterexample.Sale sale = new com.example.pos.counterexample.Sale();
        sale.onBarcode("A");
        assertEquals(1250L, sale.totalCents());
    }

    @Test
    @DisplayName("其余反例可编译运行")
    void otherCounterexamplesRun() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        assertDoesNotThrow(() -> new GodCheckoutController().settle(sale));
        assertDoesNotThrow(() -> new LegacyRegister().startSale(new Item("A", 1250L), 2));
    }
}
