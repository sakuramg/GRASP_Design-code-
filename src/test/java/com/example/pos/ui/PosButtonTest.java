// 代码2-18  PosButton 通过 setListener 更换功能：结算键改做退款，按钮代码不变
package com.example.pos.ui;

import com.example.pos.checkout.CheckoutCompletion;
import com.example.pos.checkout.CheckoutController;
import com.example.pos.checkout.PaymentReceipt;
import com.example.pos.checkout.PaymentService;
import com.example.pos.sale.Sale;
import com.example.pos.sale.SaleStatus;
import com.example.pos.tax.TaxAdapter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PosButtonTest {
    @Test
    void setListenerSwapsSettleForRefund() {
        Sale sale = new Sale();
        AtomicInteger charges = new AtomicInteger();
        CheckoutController controller = new CheckoutController(sale, new TaxAdapter(), (s, t) -> { },
                new PaymentService(a -> {
                    charges.incrementAndGet();
                    return PaymentReceipt.success("TX");
                }, Runnable::run));
        controller.onBarcodeScanned("A");

        PosButton settleKey = new PosButton("结算");
        settleKey.setListener(new SettleListener(controller, new CheckoutCompletion(controller, Runnable::run)));
        settleKey.doClick(0);                        // 无图形环境也可触发ActionEvent
        assertEquals(1, charges.get());
        assertEquals(SaleStatus.SETTLED, sale.getStatus());

        AtomicInteger refunds = new AtomicInteger();
        RefundListener refundListener = new RefundListener(refunds::incrementAndGet);
        settleKey.setListener(refundListener);       // 只改装配
        settleKey.doClick(0);
        assertEquals(1, refunds.get());
        assertEquals(1, charges.get());              // 不再触发结算
        assertEquals("结算", settleKey.getText());
    }
}
