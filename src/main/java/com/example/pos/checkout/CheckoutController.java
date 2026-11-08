package com.example.pos.checkout;

import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import com.example.pos.tax.TaxAdapter;

/**
 * 结算用例控制器：承接结算场景下的全部系统事件（2.4.1）。
 * 只做接收、判断、分发，不亲自执行业务计算。
 */
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

    /** 事件入口：收银员按下结算键 */
    public void onSettlePressed(PaymentCallback callback) {
        if (!sale.hasItems()) {
            throw new IllegalStateException("销售单为空，不能结算");
        }
        long totalCents = sale.totalCents();
        long taxCents = taxAdapter.taxOf(totalCents);
        paymentService.payAsync(sale, callback);
        printer.printReceipt(sale, taxCents);
    }

    /** 事件入口：条码枪扫入商品条码 */
    public void onBarcodeScanned(Item item, int quantity) {
        sale.addLineItem(item, quantity);
    }
}
