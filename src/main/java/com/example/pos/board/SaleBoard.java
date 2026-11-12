// 代码2-21  收银键盘与键位功能的装配
package com.example.pos.board;

import java.util.ArrayList;
import java.util.List;

public class SaleBoard {
    private final List<Button> buttons = new ArrayList<>();

    public void addButton(Button button) {
        buttons.add(button);
    }

    public Button getButton(String keyName) {
        for (Button button : buttons) {
            if (button.getKeyName().equals(keyName)) {
                return button;
            }
        }
        throw new IllegalArgumentException("未知键位：" + keyName);
    }

    public void configure() {
        // 调整键位或新增功能，只修改这里的装配语句
        getButton("M").setAction(new QueryMemberAction());
        getButton("Y").setAction(new VipPaymentAction());
        getButton("P").setAction(new ItemQueryAction());
    }
}
