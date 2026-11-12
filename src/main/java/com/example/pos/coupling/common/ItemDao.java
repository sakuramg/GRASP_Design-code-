// 代码2-12  两个类共用全局单例的数据访问对象（关键代码）——ItemDao（正文未给出源码，配套代码补充）
package com.example.pos.coupling.common;

import com.example.pos.sale.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 商品档案的全局单例数据访问对象：全系统只有一份，所有引用它的类形成公共耦合。 */
public final class ItemDao {
    private static final ItemDao INSTANCE = new ItemDao();
    private final Map<String, Item> items = new ConcurrentHashMap<>();

    private ItemDao() {
        items.put("A", new Item("A", 1250L));
        items.put("B", new Item("B", 800L));
    }

    public static ItemDao getDAO() {
        return INSTANCE;
    }

    public Item getOne(String itemId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("未找到商品：" + itemId);
        }
        return item;
    }
}
