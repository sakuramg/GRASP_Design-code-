package com.example.pos.counterexample;

import com.example.pos.sale.Sale;
import com.example.pos.sale.SaleLineItem;

/**
 * 代码2-13：承担四种变化原因的结算控制器（反例，2.3.2）。
 * 正例见 com.example.pos.checkout.CheckoutController（代码2-18）。
 */
@Deprecated
public class GodCheckoutController {

    public void settle(Sale sale) {
        long totalCents = 0L;
        for (SaleLineItem line : sale.getLineItems()) {
            totalCents += line.getItem().getUnitPriceCents() * line.getQuantity();
        }
        printReceipt(sale, totalCents);
        updateStock(sale);
        notifyCustomer(sale);
    }

    private void printReceipt(Sale sale, long totalCents) {
        System.out.println("合计：" + totalCents + "分");     // 排版并驱动打印硬件
    }

    private void updateStock(Sale sale) {
        System.out.println("UPDATE stock SET qty = qty - 1"); // 拼接库存扣减SQL
    }

    private void notifyCustomer(Sale sale) {
        System.out.println("短信：本次购买" + sale.getLineItems().size() + "项"); // 调用短信网关
    }
}
