package com.example.pos.checkout;

import com.example.pos.sale.Sale;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * 代码清单2-27：异步支付与回调的登记（片段，2.6.2）。
 * 支付服务：封装异步扣款调度并在结果返回后触发回调契约。
 */
public class PaymentService {

    private final PaymentGateway gateway;
    private final Executor executor;

    public PaymentService(PaymentGateway gateway) {
        this(gateway, ForkJoinPool.commonPool());
    }

    public PaymentService(PaymentGateway gateway, Executor executor) {
        this.gateway = gateway;
        this.executor = executor;
    }

    /** 发起异步支付：调用后立即返回，网关响应后按状态反向调用 callback */
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
