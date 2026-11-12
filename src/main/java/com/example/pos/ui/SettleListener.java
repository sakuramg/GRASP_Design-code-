// 代码2-18  通过监听接口解除按钮与功能的绑定（正例）——SettleListener
package com.example.pos.ui;

import com.example.pos.checkout.CheckoutController;
import com.example.pos.checkout.PaymentCallback;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 结算监听器：实现监听接口，把结算交给用例控制器
public class SettleListener implements ActionListener {
    private final CheckoutController controller;
    private final PaymentCallback callback;

    public SettleListener(CheckoutController controller, PaymentCallback callback) {
        this.controller = controller;
        this.callback = callback;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.onSettlePressed(callback);
    }
}
