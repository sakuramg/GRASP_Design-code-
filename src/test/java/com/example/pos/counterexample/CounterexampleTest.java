// 代码2-3 / 2-6 / 2-11 / 2-13 / 2-14 / 2-15 / 2-17  反例可运行性检查
// 反例可运行性检查：代码2-3、2-6、2-11、2-13/2-14、2-15、2-17
package com.example.pos.counterexample;

import com.example.pos.counterexample.coupling.TaxMaker;
import com.example.pos.counterexample.coupling.Taxpayer;
import com.example.pos.sale.Item;
import com.example.pos.sale.SaleLineItem;
import com.example.pos.sale.SaleStatus;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("deprecation")
class CounterexampleTest {
    @Test
    void skipLevelTotalChangesAfterRepricing() {   // 代码2-3
        Item a = new Item("A", 1250L);
        var legacy = new com.example.pos.counterexample.expert.Sale();
        legacy.addLine(new SaleLineItem(a, 2));
        var proper = new com.example.pos.sale.Sale();
        proper.addLineItem(a, 2);
        assertEquals(2500L, legacy.totalCents());
        a.setUnitPriceCents(1500L);
        assertEquals(3000L, legacy.totalCents());   // 反例：历史小票金额随调价改变
        assertEquals(2500L, proper.totalCents());   // 正例：成交价快照不变
    }

    @Test
    void registerCreatesLineDirectly() {   // 代码2-6
        assertDoesNotThrow(() -> new com.example.pos.counterexample.creator.Register()
                .startSale(new Item("A", 1250L), 2));
    }

    @Test
    void taxMakerControlFlag() {   // 代码2-11
        TaxMaker maker = new TaxMaker();
        assertEquals(500L, new Taxpayer(0).payTax(maker, 10000L));
        assertEquals(700L, new Taxpayer(1).payTax(maker, 10000L));
        assertEquals(700L, new Taxpayer(2).payTax(maker, 10000L));
        assertEquals(-1L, new Taxpayer(9).payTax(maker, 10000L));
    }

    @Test
    void contentCouplingVersusSetter() {   // 代码2-13 / 代码2-14
        var exposed = new com.example.pos.counterexample.coupling.Item("A", 1250L);
        new com.example.pos.counterexample.coupling.PriceController().priceAdjustment(Map.of(exposed, -1L));
        assertEquals(-1L, exposed.getUnitPriceCents());   // 负价未经检查写入

        Item item = new Item("A", 1250L);
        var good = new com.example.pos.coupling.PriceController();
        assertThrows(IllegalArgumentException.class, () -> good.priceAdjustment(Map.of(item, -1L)));
        assertEquals(1250L, item.getUnitPriceCents());
        good.priceAdjustment(Map.of(item, 1300L));
        assertEquals(1300L, item.getUnitPriceCents());
    }

    @Test
    void godControllerRuns() {   // 代码2-15
        var sale = new com.example.pos.sale.Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        assertDoesNotThrow(() -> new GodCheckoutController().settle(sale));
    }

    @Test
    void hardcodedSettleButtonDoesEverything() {   // 代码2-17：4400×96%＝4224，税额4224×3÷103≈123
        var sale = new com.example.pos.sale.Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        AtomicLong tax = new AtomicLong(-1L);
        new SettleButton(sale, (s, t) -> tax.set(t), 96).actionPerformed(null);
        assertEquals(123L, tax.get());
        assertEquals(SaleStatus.SETTLED, sale.getStatus());
    }
}
