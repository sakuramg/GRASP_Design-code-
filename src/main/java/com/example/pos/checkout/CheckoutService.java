// 代码2-8  依据第一章代码1-2改写的打印接口与注入——CheckoutService
package com.example.pos.checkout;

import com.example.pos.sale.Sale;

import java.util.Objects;

/** 结算服务只依赖抽象的 Printer，具体设备由外部注入（与正文一致为包级可见）。 */
final class CheckoutService {
    private final Printer printer;                         // 仅依赖稳定接口

    CheckoutService(Printer printer) {
        this.printer = Objects.requireNonNull(printer);    // 具体实现由外部注入
    }

    public void printReceipt(Sale sale, long taxCents) {
        printer.printReceipt(sale, taxCents);
    }
}
