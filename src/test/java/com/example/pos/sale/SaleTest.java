// 代码2-5  小票职责分配的断言
package com.example.pos.sale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleTest {
    @Test
    void subtotalAndTotalMatchTheFixedReceipt() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        assertEquals(2500L, sale.getLineItems().get(0).subtotalCents());   // A行：1250×2
        assertEquals(2400L, sale.getLineItems().get(1).subtotalCents());
        assertEquals(4900L, sale.totalCents());
        assertEquals(4400L, sale.amountDueCents());                        // 整单满4000分减500分
    }

    // ---- 以下为配套代码补充的断言 ----

    @Test
    void chapter1RecordModelGivesSameNumbers() {   // 代码2-1
        var sale = new Chapter1ReceiptDemo.Sale(java.util.List.of(
                new Chapter1ReceiptDemo.SaleLineItem(1250L, 2),
                new Chapter1ReceiptDemo.SaleLineItem(800L, 3)));
        assertEquals(4900L, sale.totalCents());
        assertEquals(4400L, sale.amountDueCents());
    }

    @Test
    void dealPriceSnapshotSurvivesPriceChange() {   // 代码2-2：调价后历史小票不变
        Sale sale = new Sale();
        Item a = new Item("A", 1250L);
        sale.addLineItem(a, 2);
        a.setUnitPriceCents(1500L);
        assertEquals(2500L, sale.totalCents());
        assertEquals(1250L, sale.getLineItems().get(0).getDealUnitPriceCents());
    }

    @Test
    void registerCreatesSaleAndSaleCreatesLines() {   // 代码2-7
        Sale sale = new Register().newSale();
        sale.addLineItem(ItemCatalog.lookup("A"), 2);
        assertEquals(1, sale.getLineItems().size());
        assertThrows(UnsupportedOperationException.class,
                () -> sale.getLineItems().add(new SaleLineItem(new Item("X", 1L), 1)));
    }

    @Test
    void invalidInputsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SaleLineItem(new Item("A", 1250L), 0));
        assertThrows(IllegalArgumentException.class, () -> new Item("A", -1L));
        assertEquals(0L, new Sale().amountDueCents());
    }
}
