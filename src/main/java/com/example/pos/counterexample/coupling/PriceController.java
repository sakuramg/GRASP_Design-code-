// 代码2-13  调价时直接修改商品内部字段（反例）
package com.example.pos.counterexample.coupling;

import java.util.Map;

/** 内容耦合反例，仅供对照。正例见 com.example.pos.coupling.PriceController（代码2-14）。 */
@Deprecated
public class PriceController {
    // 接收前端页面提交的商品调价价格
    public void priceAdjustment(Map<Item, Long> items) {
        for (Item item : items.keySet()) {
            long newPrice = items.get(item);
            item.unitPriceCents = newPrice;    // 非正常入口，价格未经任何检查
        }
    }
}
