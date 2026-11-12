// 代码2-18  通过监听接口解除按钮与功能的绑定（正例）——PosButton
package com.example.pos.ui;

import javax.swing.JButton;
import java.awt.event.ActionListener;

// 普通按钮：不包含任何功能，只持有抽象的监听接口
public class PosButton extends JButton {
    private ActionListener listener;

    public PosButton(String text) {
        setText(text);
        setSize(120, 70);
        addActionListener(e -> {
            if (listener != null) {
                listener.actionPerformed(e);    // 调用约定好的抽象方法
            }
        });
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
}
