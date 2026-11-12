package com.example.pos.counterexample;

import java.util.Map;

/**
 * 代码2-11：通过非正常入口修改内部字段（反例，2.3.1）。
 * 正式的 com.example.pos.sale.Item 把单价声明为 private，反例无法编译；
 * 为让反例可运行，这里用嵌套类 Item 模拟“字段被暴露”的商品。正例见 com.example.pos.coupling.PriceController（代码2-12）。
 */
@Deprecated
public class PriceController {

    /** 反例专用：单价字段对外可写的商品 */
    public static class Item {
        private final String name;
        public long unitPriceCents;              // 反例：字段未封装

        public Item(String name, long unitPriceCents) {
            this.name = name;
            this.unitPriceCents = unitPriceCents;
        }

        public String getName() {
            return name;
        }
    }

    public void adjustPrice(Map<Item, Long> items) {
        for (Map.Entry<Item, Long> e : items.entrySet()) {
            e.getKey().unitPriceCents = e.getValue();    // 绕过商品自身校验
        }
    }
}
