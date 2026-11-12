// 代码2-29 / 代码2-28 所依赖的支撑类型（正文未单列清单）
package com.example.pos.checkout;

/** 支付网关返回的凭据。 */
public record PaymentReceipt(boolean pending, String tradeNo, String message) {

    public static PaymentReceipt success(String tradeNo) {
        return new PaymentReceipt(false, tradeNo, "ok");
    }

    public static PaymentReceipt pending(String tradeNo) {
        return new PaymentReceipt(true, tradeNo, "pending");
    }

    public boolean isPending() {
        return pending;
    }
}
