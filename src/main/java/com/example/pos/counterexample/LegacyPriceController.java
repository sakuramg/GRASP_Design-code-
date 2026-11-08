package com.example.pos.counterexample;

import com.example.pos.sale.Item;

import java.util.Map;

/**
 * 代码清单2-5 反例留存：绕过商品接口直接改字段会产生内容耦合。
 * 正解见 com.example.pos.sale.Item#changePrice(long)。
 */
@Deprecated
public class LegacyPriceController {

    public void adjustPrice(Map<Item, Long> items) {
        for (Item item : items.keySet()) {
            Long newPriceCents = items.get(item);
            // 反例：真实代码里这里是 item.priceCents = newPriceCents;
            // 本仓库用注释保留，因为字段是 private，无法从外部写入。
            throw new UnsupportedOperationException(
                    "示意：绕过接口直接写字段，应改用 item.changePrice(" + newPriceCents + ")");
        }
    }
}
