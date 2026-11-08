package com.example.pos.sale;

/**
 * 销售流水行。掌握数量并关联商品，因此负责计算本行小计（信息专家）。
 */
public class SaleLineItem {

    private final Item item;
    private final int quantity;
    private final long unitPriceCents;   // 成交单价快照：调价后旧小票仍可复现

    public SaleLineItem(Item item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("流水行必须关联商品");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("数量必须为正：" + quantity);
        }
        this.item = item;
        this.quantity = quantity;
        this.unitPriceCents = item.getUnitPriceCents();
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    /** 行小计：所需信息（成交单价与数量）由本对象直接持有。 */
    public long subtotalCents() {
        return Math.multiplyExact(unitPriceCents, quantity);
    }
}
