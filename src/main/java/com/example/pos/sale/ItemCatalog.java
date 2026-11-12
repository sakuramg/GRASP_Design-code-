// 代码2-19  结算用例的控制器（正例·片段）所调用的商品档案查询 ItemCatalog.lookup(barcode)
package com.example.pos.sale;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 商品档案查询：按条码查找商品；预置第一章固定小票的商品A（1250分）与商品B（800分）。 */
public final class ItemCatalog {

    private static final Map<String, Item> ITEMS = new ConcurrentHashMap<>();

    static {
        ITEMS.put("A", new Item("A", 1250L));
        ITEMS.put("B", new Item("B", 800L));
    }

    private ItemCatalog() {
    }

    public static void register(String barcode, Item item) {
        ITEMS.put(barcode, item);
    }

    public static Item lookup(String barcode) {
        Item item = ITEMS.get(barcode);
        if (item == null) {
            throw new IllegalArgumentException("未找到条码对应的商品：" + barcode);
        }
        return item;
    }
}
