package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/**
 * 完成处理器：协调业务状态并通过界面入口显示结果（2.6.2）。
 */
public class CheckoutCompletion implements PaymentCallback {

    private final UiExecutor uiExecutor;

    public CheckoutCompletion(UiExecutor uiExecutor) {
        this.uiExecutor = uiExecutor;
    }

    @Override
    public void onSuccess(Sale sale) {
        sale.close(true);
        uiExecutor.execute(() -> show("支付成功，合计：" + sale.totalCents() + "分"));
    }

    @Override
    public void onFailure(Sale sale, String reason) {
        uiExecutor.execute(() -> show("支付失败：" + reason));
    }

    @Override
    public void onPending(Sale sale, String tradeNo) {
        sale.markPending(tradeNo);
        uiExecutor.execute(() -> show("支付结果确认中，请稍候"));
    }

    private void show(String message) {
        // 教学骨架：真实系统调用界面组件
        System.out.println(message);
    }
}
