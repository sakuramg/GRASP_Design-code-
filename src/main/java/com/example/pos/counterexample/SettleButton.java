// 代码2-17  把功能写死在界面控件中（反例）
package com.example.pos.counterexample;

import com.example.pos.checkout.Printer;
import com.example.pos.sale.Sale;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 反例，仅供对照。正例见 com.example.pos.ui.PosButton 与 SettleListener（代码2-18）。
 * 只依赖 javax.swing，可在无图形环境（headless）下编译和构造。
 */
@Deprecated
public class SettleButton extends JButton implements ActionListener {
    private final Sale sale;
    private final Printer printer;
    private final int discountPercent;          // 会员折扣百分数

    public SettleButton(Sale sale, Printer printer, int discountPercent) {
        this.sale = sale;
        this.printer = printer;
        this.discountPercent = discountPercent;
        setText("结算");
        setSize(120, 70);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 反例：结算功能写死在按钮内部
        long payableCents = sale.amountDueCents() * discountPercent / 100;
        printer.printReceipt(sale, sale.taxCents(payableCents));
        sale.close(true);
    }
}
