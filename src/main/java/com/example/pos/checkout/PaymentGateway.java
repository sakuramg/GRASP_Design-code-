package com.example.pos.checkout;

/** 外部支付平台接口；不同平台由各自的适配器实现。 */
public interface PaymentGateway {

    PaymentReceipt charge(long amountCents);
}
