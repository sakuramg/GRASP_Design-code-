// 代码2-29  异步支付与回调的登记（片段）
package com.example.pos.checkout;

import com.example.pos.sale.Sale;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/** 回调的保存者：按优惠后应付金额 amountDueCents 异步扣款，结果返回后按回执状态调用回调接口。 */
public class PaymentService {
    private final PaymentGateway gateway;
    private final Executor executor;

    public PaymentService(PaymentGateway gateway, Executor executor) {
        this.gateway = gateway;
        this.executor = executor;
    }

    public void payAsync(Sale sale, PaymentCallback callback) {
        CompletableFuture
                .supplyAsync(() -> gateway.charge(sale.amountDueCents()), executor)
                .whenComplete((receipt, ex) -> {
                    if (ex != null) {
                        callback.onFailure(sale, ex.getMessage());  // 适配器只对明确拒绝抛出异常
                    } else if (receipt.isPending()) {
                        callback.onPending(sale, receipt.tradeNo());
                    } else {
                        callback.onSuccess(sale);
                    }
                });
    }
}
