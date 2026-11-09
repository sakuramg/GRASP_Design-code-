package com.example.pos.checkout;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

/**
 * 代码清单2-16：基于抽象事件接收接口的界面解耦（正例，2.4.1）。
 */
public class SettleButton extends JButton {

    private EventSink sink;                     // 仅持有抽象契约引用

    public SettleButton() {
        super("结算");
    }

    public void setSink(EventSink sink) {
        this.sink = sink;
    }

    public void fireActionPerformed() {
        actionPerformed(null);
    }

    protected void actionPerformed(ActionEvent e) {
        if (sink != null) {
            sink.onEvent(SystemOperation.SETTLE, null);
        }
    }
}
