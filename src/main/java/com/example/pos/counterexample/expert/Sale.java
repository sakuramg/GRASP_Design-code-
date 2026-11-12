// 代码2-3  小票合计的越级计算（反例）
package com.example.pos.counterexample.expert;

import com.example.pos.sale.SaleLineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 反例，仅供对照。正例见 com.example.pos.sale.Sale#totalCents()（代码2-4）。
 * 依赖代码2-2的 SaleLineItem 额外公开的 getItem()/getQuantity()。
 * 正文片段没有添加明细的入口；这里补一个 addLine 仅为让测试能演示“调价后历史金额改变”。
 */
@Deprecated
public class Sale {
    private final List<SaleLineItem> lineItems = new ArrayList<>();

    public long totalCents() {
        long total = 0L;
        for (SaleLineItem line : lineItems) {
            // 越级读取商品当前标价，并在Sale中重复计算行小计
            long price = line.getItem().getUnitPriceCents();
            total += price * line.getQuantity();
        }
        return total;
    }

    /** 配套代码补充：仅用于测试装入明细。 */
    public void addLine(SaleLineItem line) {
        lineItems.add(line);
    }
}
