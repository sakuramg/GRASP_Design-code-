// 代码2-20  按键与功能分离（正例）——Button
package com.example.pos.board;

public class Button {
    private final String keyName;
    private Action action;                    // 只引用抽象功能，不引用具体功能类

    public Button(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void onClick() {
        if (action != null) {
            action.actionPerformed();         // 委托给注入的功能对象
        }
    }
}
