// 代码2-13  调价时直接修改商品内部字段（反例）所假设的“单价字段未声明为private”的商品
package com.example.pos.counterexample.coupling;

/**
 * 反例专用商品：正文假设 Item 的 unitPriceCents 未声明为 private。
 * 正式的 com.example.pos.sale.Item 字段为 private，只能经 setUnitPriceCents 修改（代码2-14）。
 */
@Deprecated
public class Item {
    private final String name;
    long unitPriceCents;                      // 反例：字段未声明为private（包内可直接读写）

    public Item(String name, long unitPriceCents) {
        this.name = name;
        this.unitPriceCents = unitPriceCents;
    }

    public String getName() {
        return name;
    }

    public long getUnitPriceCents() {
        return unitPriceCents;
    }
}
