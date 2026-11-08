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
