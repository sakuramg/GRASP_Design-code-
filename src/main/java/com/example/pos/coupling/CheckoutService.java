package com.example.pos.coupling;

import java.util.Objects;

/**
 * 代码2-8：依据第一章代码1-2改写的打印接口与注入（，2.3.1）。
 */
public final class CheckoutService {

    private final ReceiptPrinter printer;                  // 仅依赖稳定接口

    public CheckoutService(ReceiptPrinter printer) {
        this.printer = Objects.requireNonNull(printer);    // 具体实现由外部注入
    }

    public void printReceiptText(String text) {
        printer.print(text);
    }
}
