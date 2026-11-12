// 代码2-12  两个类共用全局单例的数据访问对象（关键代码）——DaySumItem
package com.example.pos.coupling.common;

import com.example.pos.sale.Item;

public class DaySumItem {
    private Item item;

    void loadItem(String itemId) {
        ItemDao dao = ItemDao.getDAO();        // 与SaleLineItem共用同一实例
        item = dao.getOne(itemId);
    }

    Item getItem() {
        return item;
    }
}
