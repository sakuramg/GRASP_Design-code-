package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 代码2-27：结果处理的实现（片段，2.6.2）。
 * 支付完成回调处理器：协调小票状态变更、触发后续打印并切回UI线程刷新界面。
 */
public class CheckoutCompletion implements PaymentCallback {

    private final CheckoutController controller;
    private final UiExecutor uiExecutor;

    public CheckoutCompletion(CheckoutController controller, UiExecutor uiExecutor) {
        this.controller = controller;
        this.uiExecutor = uiExecutor;
    }

    @Override
    public void onSuccess(Sale sale) {
        if (sale.isClosed()) {
            return;                                     // 重复通知不再处理
        }
        sale.close(true);                               // 状态变更由销售单完成
        controller.onPaid(controller.calculateTaxCents());  // 驱动打印小票
        uiExecutor.execute(() ->
                showMessage("支付成功，应付：" + sale.amountDueCents() + "分"));
    }

    @Override
    public void onFailure(Sale sale, String reason) {
        uiExecutor.execute(() -> showMessage("支付失败：" + reason));
    }

    @Override
    public void onPending(Sale sale, String tradeNo) {
        sale.markPending(tradeNo);                      // 登记流水号，不按失败处理
        uiExecutor.execute(() -> showMessage("等待顾客在手机端确认支付，请稍候"));
    }

    private void showMessage(String message) {
        System.out.println(message);
    }
}
