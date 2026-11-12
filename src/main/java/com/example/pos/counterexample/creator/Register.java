// 代码2-6  在Register中越级创建流水对象（反例）——Register部分
package com.example.pos.counterexample.creator;

import com.example.pos.sale.Item;
import com.example.pos.sale.SaleLineItem;

/** 反例，仅供对照。正例见 com.example.pos.sale.Register（代码2-7）。 */
@Deprecated
public class Register {
    public void startSale(Item item, int quantity) {
        LegacySale sale = new LegacySale();                    // 创建小票：合理
        SaleLineItem line = new SaleLineItem(item, quantity);  // 越级创建流水：不合理
        sale.addLineItem(line);
    }
}
