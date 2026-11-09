package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 代码清单2-25：支付结果的回调契约（2.6.2）。
 * 本地支付结果回调契约；第三方支付平台的原始报文需先由支付适配器转换为此契约调用。
 */
public interface PaymentCallback {

    void onSuccess(Sale sale);                  // 支付成功：平台已确认扣款完成

    void onFailure(Sale sale, String reason);   // 支付失败：扣款被拒绝或发生明确异常

    void onPending(Sale sale, String tradeNo);  // 结果待确认：如等待顾客在手机端输入支付密码
}
