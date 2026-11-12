// 代码2-2  SaleLineItem保存成交价快照
package com.example.pos.sale;

import java.util.Objects;

/**
 * 交易明细：保存商品引用、购买数量与成交单价快照，负责计算本行小计（信息专家原则）。
 * getItem()/getQuantity() 是正文所述“代码2-3反例需要在代码2-2基础上再公开的两个取值方法”。
 */
public class SaleLineItem {

    private final Item item;
    private final int quantity;
    private final long dealUnitPriceCents;                      // 保存成交当时的单价快照

    public SaleLineItem(Item item, int quantity) {
        this.item = Objects.requireNonNull(item, "商品不能为空");
        if (quantity <= 0) {
            throw new IllegalArgumentException("购买数量必须为正整数");
        }
        this.quantity = quantity;
        this.dealUnitPriceCents = item.getUnitPriceCents();
    }

    public long subtotalCents() {
        return Math.multiplyExact(dealUnitPriceCents, quantity);
    }

    /** 供代码2-3、代码2-15反例越级取数使用。 */
    public Item getItem() {
        return item;
    }

    /** 供代码2-3、代码2-15反例越级取数使用。 */
    public int getQuantity() {
        return quantity;
    }

    public long getDealUnitPriceCents() {
        return dealUnitPriceCents;
    }
}
