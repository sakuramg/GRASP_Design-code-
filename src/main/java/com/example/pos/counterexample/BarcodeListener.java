package com.example.pos.counterexample;

/**
 * 代码2-16：业务对象直接监听外部设备事件（反例·片段，2.4.1）——硬件监听接口。
 */
@Deprecated
public interface BarcodeListener {

    void onBarcode(String barcode);
}
