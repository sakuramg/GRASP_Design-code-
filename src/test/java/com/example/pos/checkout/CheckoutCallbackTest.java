// 代码2-19 / 代码2-27 / 代码2-28 / 代码2-29  控制器、回调接口与异步支付的三种结果
package com.example.pos.checkout;

import com.example.pos.sale.Sale;
import com.example.pos.sale.SaleStatus;
import com.example.pos.tax.TaxAdapter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutCallbackTest {
    private final AtomicInteger prints = new AtomicInteger();
    private final AtomicLong printedTax = new AtomicLong(-1L);
    private final AtomicLong charged = new AtomicLong(-1L);

    private CheckoutController controller(Sale sale, PaymentGateway gateway) {
        PaymentService service = new PaymentService(gateway, Runnable::run);   // 同步执行器便于断言
        Printer printer = (s, tax) -> {
            prints.incrementAndGet();
            printedTax.set(tax);
        };
        return new CheckoutController(sale, new TaxAdapter(), printer, service);
    }

    private static void scanFixedReceipt(CheckoutController c) {
        c.onBarcodeScanned("A");
        c.onBarcodeScanned("A");
        c.onBarcodeScanned("B");
        c.onBarcodeScanned("B");
        c.onBarcodeScanned("B");
    }

    @Test
    void successClosesSalePrintsOnceAndIgnoresDuplicate() {
        Sale sale = new Sale();
        CheckoutController c = controller(sale, amount -> {
            charged.set(amount);
            return PaymentReceipt.success("TX-1");
        });
        scanFixedReceipt(c);
        assertEquals(4900L, sale.totalCents());
        CheckoutCompletion completion = new CheckoutCompletion(c, Runnable::run);
        c.onSettlePressed(completion);
        assertEquals(4400L, charged.get());          // 按amountDueCents扣款
        assertEquals(SaleStatus.SETTLED, sale.getStatus());
        assertEquals(1, prints.get());
        assertEquals(128L, printedTax.get());
        completion.onSuccess(sale);                  // 重复通知
        assertEquals(1, prints.get());
    }

    @Test
    void failureKeepsSaleOpenAndPassesReason() {
        Sale sale = new Sale();
        CheckoutController c = controller(sale, amount -> {
            throw new IllegalStateException("余额不足");
        });
        c.onBarcodeScanned("A");
        AtomicReference<String> reason = new AtomicReference<>();
        c.onSettlePressed(new CheckoutCompletion(c, Runnable::run) {
            @Override
            public void onFailure(Sale s, String r) {
                reason.set(r);
                super.onFailure(s, r);
            }
        });
        assertEquals(SaleStatus.OPEN, sale.getStatus());
        assertEquals(0, prints.get());
        assertTrue(reason.get().contains("余额不足"));
    }

    @Test
    void pendingRecordsTradeNoWithoutClosingOrPrinting() {
        Sale sale = new Sale();
        CheckoutController c = controller(sale, amount -> PaymentReceipt.pending("TX-P-9"));
        c.onBarcodeScanned("A");
        assertNull(sale.getPendingTradeNo());
        c.onSettlePressed(new CheckoutCompletion(c, Runnable::run));
        assertEquals(SaleStatus.PENDING, sale.getStatus());
        assertEquals("TX-P-9", sale.getPendingTradeNo());
        assertEquals(0, prints.get());
    }

    @Test
    void emptySaleCannotSettle() {
        CheckoutController c = controller(new Sale(), amount -> PaymentReceipt.success("X"));
        assertThrows(IllegalStateException.class,
                () -> c.onSettlePressed(new CheckoutCompletion(c, Runnable::run)));
    }

    @Test
    void checkoutServiceDelegatesToInjectedPrinter() {   // 代码2-8
        AtomicLong tax = new AtomicLong(-1L);
        new CheckoutService((s, t) -> tax.set(t)).printReceipt(new Sale(), 128L);
        assertEquals(128L, tax.get());
        assertThrows(NullPointerException.class, () -> new CheckoutService(null));
    }
}
