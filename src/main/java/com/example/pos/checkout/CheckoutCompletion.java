package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 代码清单2-26：结果处理的实现（片段，2.6.2）。
 * 支付完成回调处理器：协调小票状态变更、触发后续打印并切回UI线程刷新界面。
 */
public class CheckoutCompletion implements PaymentCallback {

    private final CheckoutController controller;
    private final UiExecutor uiExecutor;

    public CheckoutCompletion(CheckoutController controller, UiExecutor uiExecutor) {
        this.controller = controller;
        this.uiExecutor = uiExecutor;
    }

    public CheckoutCompletion(UiExecutor uiExecutor) {
        this(null, uiExecutor);
    }

    @Override
    public void onSuccess(Sale sale) {
        if (sale.isClosed()) {
            return;                                     // 幂等保护：已关闭的小票忽略重复回调
        }
        sale.close(true);                               // 状态变更归小票自己（信息专家）
        if (controller != null) {
            long taxCents = controller.calculateTaxCents(); // 委托计算税额
            controller.onPaid(taxCents);                    // 通知控制器驱动打印小票
        }
        uiExecutor.execute(() ->
                showMessage("支付成功，合计：" + sale.totalCents() + "分"));
    }

    @Override
    public void onFailure(Sale sale, String reason) {
        uiExecutor.execute(() -> showMessage("支付失败：" + reason));
    }

    @Override
    public void onPending(Sale sale, String tradeNo) {
        // 结果尚未确定：登记平台流水号并标记待确认，严禁直接当作失败处理
        sale.markPending(tradeNo);
        uiExecutor.execute(() -> showMessage("等待顾客在手机端确认支付，请稍候"));
    }

    private void showMessage(String message) {
        System.out.println(message);
    }
}
