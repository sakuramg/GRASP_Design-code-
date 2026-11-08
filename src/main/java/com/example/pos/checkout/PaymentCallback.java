package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 本地支付回调契约；远程结果需先由支付适配器转换（2.6.2）。
 */
public interface PaymentCallback {

    void onSuccess(Sale sale);

    void onFailure(Sale sale, String reason);

    void onPending(Sale sale, String tradeNo);
}
