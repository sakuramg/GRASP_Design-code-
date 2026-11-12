// 代码2-1  依据第一章代码1-3改写的行小计、汇总与整单优惠
package com.example.pos.sale;

import java.util.List;

/**
 * 正文中两个 record 是顶层类型；这里嵌套在 Chapter1ReceiptDemo 中，以免与正式类 Sale、SaleLineItem 重名。
 */
public final class Chapter1ReceiptDemo {

    private Chapter1ReceiptDemo() {
    }

    public record SaleLineItem(long unitPriceCents, int quantity) {
        public long subtotalCents() {
            return Math.multiplyExact(unitPriceCents, quantity);   // 行小计归明细行
        }
    }

    public record Sale(List<SaleLineItem> lines) {
        public long totalCents() {
            long total = 0L;
            for (SaleLineItem line : lines) {
                total = Math.addExact(total, line.subtotalCents()); // 汇总只调用契约
            }
            return total;
        }

        public long amountDueCents() {
            long total = totalCents();
            return total >= 4000L ? total - 500L : total;           // 整单满4000分减500分
        }
    }
}
