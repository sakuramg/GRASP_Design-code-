// 代码2-14  通过商品公开的方法调价（正例）
package com.example.pos.coupling;

import com.example.pos.sale.Item;

import java.util.Map;

/** 调价只经 Item 的合法入口 setUnitPriceCents，内容耦合降为数据耦合。反例见 counterexample.coupling.PriceController（代码2-13）。 */
public class PriceController {
    public void priceAdjustment(Map<Item, Long> items) {
        for (Item item : items.keySet()) {
            item.setUnitPriceCents(items.get(item));   // 负数由商品对象拒绝
        }
    }
}
