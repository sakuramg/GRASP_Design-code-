// 代码2-12  两个类共用全局单例的数据访问对象（关键代码）——SaleLineItem
package com.example.pos.coupling.common;

import com.example.pos.sale.Item;

/**
 * 公共耦合示例中的流水类（只展示加载商品的部分）。
 * 完整的流水类见 com.example.pos.sale.SaleLineItem（代码2-2）；Java 中同名类须分属不同包。
 */
public class SaleLineItem {
    private Item item;

    void loadItem(String itemId) {
        ItemDao dao = ItemDao.getDAO();        // 全局单例
        item = dao.getOne(itemId);
    }

    Item getItem() {
        return item;
    }
}
