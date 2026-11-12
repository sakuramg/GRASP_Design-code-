package com.example.pos.counterexample;

import com.example.pos.checkout.Printer;
import com.example.pos.sale.Sale;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 代码2-15：把功能写死在界面控件中（反例，2.4.1）。
 * 正例见 com.example.pos.checkout.SettleButton 与 EventSink（代码2-17）。
 */
@Deprecated
public class HardcodedSettleButton extends JButton implements ActionListener {

    private final Sale sale;
    private final Printer printer;
    private final int discountPercent;          // 会员折扣百分数

    public HardcodedSettleButton(Sale sale, Printer printer, int discountPercent) {
        super("结算");
        this.sale = sale;
        this.printer = printer;
        this.discountPercent = discountPercent;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 反例：算价、计税、打印和改状态全部写在按钮内部
        long payableCents = sale.amountDueCents() * discountPercent / 100;
        printer.printReceipt(sale, sale.taxCents(payableCents));
        sale.close(true);
    }
}
