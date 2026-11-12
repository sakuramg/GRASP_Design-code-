package com.example.pos.counterexample;

import com.example.pos.sale.ItemCatalog;

/**
 * 代码2-16：业务对象直接监听外部设备事件（反例·片段，2.4.1）。
 * 正文为片段；为可编译，此处继承正式 Sale 以复用 addLineItem。
 */
@Deprecated
public class Sale extends com.example.pos.sale.Sale implements BarcodeListener {

    // 反例：领域对象直接实现硬件监听接口
    @Override
    public void onBarcode(String barcode) {
        addLineItem(ItemCatalog.lookup(barcode), 1);
    }
}
