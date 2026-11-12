package com.example.pos.coupling;

import com.example.pos.sale.Item;

import java.util.Map;

/**
 * 代码2-12：通过接口修改并保留校验（正例，2.3.1）。
 */
public class PriceController {

    public void adjustPrice(Map<Item, Long> items) {
        for (Map.Entry<Item, Long> e : items.entrySet()) {
            e.getKey().changePrice(e.getValue());        // 负数由商品对象拒绝
        }
    }
}
