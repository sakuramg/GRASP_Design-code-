package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 代码2-26：支付结果的回调接口（2.6.2）。
 * 本地支付结果回调契约；第三方支付平台的原始报文需先由支付适配器转换为此契约调用。
 */
public interface PaymentCallback {

    void onSuccess(Sale sale);                  // 平台已确认扣款完成

    void onFailure(Sale sale, String reason);   // 平台明确拒绝，本次未扣款

    void onPending(Sale sale, String tradeNo);  // 结果待确认，保留原交易流水号
}
