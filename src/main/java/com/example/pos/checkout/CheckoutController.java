// 代码2-19  结算用例的控制器（正例·片段）
package com.example.pos.checkout;

import com.example.pos.sale.Item;
import com.example.pos.sale.ItemCatalog;
import com.example.pos.sale.Sale;
import com.example.pos.tax.TaxAdapter;

// 结算用例控制器：只接收事件、检查前置条件并按序委托
public class CheckoutController {
    private final Sale sale;
    private final TaxAdapter taxAdapter;
    private final Printer printer;
    private final PaymentService paymentService;

    public CheckoutController(Sale sale, TaxAdapter taxAdapter,
                              Printer printer, PaymentService paymentService) {
        this.sale = sale;
        this.taxAdapter = taxAdapter;
        this.printer = printer;
        this.paymentService = paymentService;
    }

    // 系统事件：扫入商品条码
    public void onBarcodeScanned(String barcode) {
        Item item = ItemCatalog.lookup(barcode);
        sale.addLineItem(item, 1);
    }

    // 系统事件：发起结算，支付异步执行
    public void onSettlePressed(PaymentCallback callback) {
        if (!sale.hasItems()) {
            throw new IllegalStateException("销售单为空，不能发起结算");
        }
        paymentService.payAsync(sale, callback);
    }

    // 支付确认后驱动打印
    public void onPaid(long taxCents) {
        printer.printReceipt(sale, taxCents);
    }

    public long calculateTaxCents() {
        return taxAdapter.taxOf(sale.amountDueCents());
    }
}
