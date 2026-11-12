// 代码2-27  支付结果的回调接口
package com.example.pos.checkout;

import com.example.pos.sale.Sale;

public interface PaymentCallback {
    void onSuccess(Sale sale);                  // 平台已确认扣款完成
    void onFailure(Sale sale, String reason);   // 平台明确拒绝，本次未扣款
    void onPending(Sale sale, String tradeNo);  // 要求输入密码等待确认，保留流水号
}
