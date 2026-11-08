package com.example.pos.sale;

/**
 * 商品信息对象（教学简化）。
 * 金额单位为整数分，字段与方法以 Cents 结尾。
 */
public class Item {

    private final String name;
    private long unitPriceCents;

    public Item(String name, long unitPriceCents) {
        this.name = name;
        changePrice(unitPriceCents);
    }

    public String getName() {
        return name;
    }

    public long getUnitPriceCents() {
        return unitPriceCents;
    }

    /**
     * 修改价格。校验由商品自己负责，调用方不可能绕过。
     * 代码清单2-6 的正例就是改走这个入口。
     */
    public void changePrice(long newPriceCents) {
        if (newPriceCents < 0) {
            throw new IllegalArgumentException("价格不能为负：" + newPriceCents);
        }
        this.unitPriceCents = newPriceCents;
    }
}
