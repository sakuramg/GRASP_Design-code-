package com.example.pos.counterexample;

import com.example.pos.sale.SaleLineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码清单2-1 反例留存：Sale 越过流水直接取单价（保留以便对照）。
 * 正解见 com.example.pos.sale.Sale。
 */
@Deprecated
public class LegacySale {

    private final List<SaleLineItem> lineItems = new ArrayList<>();

    public long getTotalCents() {
        long total = 0L;
        for (SaleLineItem line : lineItems) {
            // 越级：Sale 直接向商品要单价
            long price = line.getItem().getUnitPriceCents();
            total += price * line.getQuantity();
        }
        return total;
    }
}
