// 代码2-2  SaleLineItem保存成交价快照（Item部分：商品名称与当前标价）
// 代码2-14 通过商品公开的方法调价（正例）所调用的 setUnitPriceCents 合法入口
package com.example.pos.sale;

/**
 * 商品：保存名称和当前标价，金额单位为整数分。
 * 单价字段为 private，只能经 setUnitPriceCents 修改，负数由商品对象拒绝。
 */
public class Item {

    private final String name;
    private long unitPriceCents;

    public Item(String name, long unitPriceCents) {
        this.name = name;
        setUnitPriceCents(unitPriceCents);
    }

    public String getName() {
        return name;
    }

    public long getUnitPriceCents() {
        return unitPriceCents;
    }

    /** 合法入口：先检查价格不小于零，再修改内部字段。 */
    public void setUnitPriceCents(long unitPriceCents) {
        if (unitPriceCents < 0) {
            throw new IllegalArgumentException("价格不能为负：" + unitPriceCents);
        }
        this.unitPriceCents = unitPriceCents;
    }
}
