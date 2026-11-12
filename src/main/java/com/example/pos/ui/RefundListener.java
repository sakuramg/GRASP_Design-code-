// 代码2-18  正文所述的退款监听器 RefundListener（settleKey.setListener(refundListener)），正文未给出源码
package com.example.pos.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 退款监听器：与 SettleListener 实现同一 ActionListener 接口，换键功能只需 setListener。
 * 退款流程不在本章范围内，这里把具体退款动作注入为 Runnable（配套代码补充）。
 */
public class RefundListener implements ActionListener {
    private final Runnable refund;

    public RefundListener(Runnable refund) {
        this.refund = refund;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refund.run();
    }
}
