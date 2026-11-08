package com.example.pos.checkout;

import com.example.pos.sale.Sale;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 支付服务：教学骨架。异步支付，结果出来后回调，调用方不死等（2.6）。
 */
public class PaymentService {

    private final PaymentGateway gateway;
    private final Executor executor;

    public PaymentService(PaymentGateway gateway, Executor executor) {
        this.gateway = gateway;
        this.executor = executor;
    }

    public void payAsync(Sale sale, PaymentCallback callback) {
        CompletableFuture
                .supplyAsync(() -> gateway.charge(sale.totalCents()), executor)
                .whenComplete((receipt, ex) -> {
                    if (ex != null) {
                        callback.onFailure(sale, ex.getMessage());
                    } else if (receipt.isPending()) {
                        callback.onPending(sale, receipt.tradeNo());
                    } else {
                        callback.onSuccess(sale);
                    }
                });
    }
}
