package com.example.pos.sale;

import com.example.pos.member.MemberCard;
import com.example.pos.tax.TaxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 销售单领域对象（代码2-4、2-7、2-22、2-25）。
 * 掌握明细行集合，负责汇总整单金额（信息专家原则）；
 * 与 SaleLineItem 为组合关系，负责创建明细行（创建者原则）。
 */
public class Sale {

    private final List<SaleLineItem> lineItems = new ArrayList<>();
    private TaxAdapter taxAdapter = new TaxAdapter();
    private SaleStatus status = SaleStatus.OPEN;
    private String pendingTradeNo;

    public Sale() {
    }

    /** 便捷构造：等价于 new Sale() 后调用 setTaxAdapter。 */
    public Sale(TaxAdapter taxAdapter) {
        setTaxAdapter(taxAdapter);
    }

    /** 代码2-25：由装配代码替换计税适配器。 */
    public void setTaxAdapter(TaxAdapter taxAdapter) {
        this.taxAdapter = Objects.requireNonNull(taxAdapter, "计税适配器不能为空");
    }

    /** 代码2-4、2-7：明细由小票创建（组合关系，流水行不能离开小票单独存在）。 */
    public void addLineItem(Item item, int quantity) {
        SaleLineItem line = new SaleLineItem(item, quantity);
        lineItems.add(line);
    }

    public List<SaleLineItem> getLineItems() {
        return List.copyOf(lineItems);
    }

    public boolean hasItems() {
        return !lineItems.isEmpty();
    }

    /** 代码2-4 合计：只汇总各行小计，不越级读取商品单价（信息专家原则）。 */
    public long totalCents() {
        long total = 0L;
        for (SaleLineItem line : lineItems) {
            total = Math.addExact(total, line.subtotalCents());
        }
        return total;
    }

    /**
     * 整单应付：沿用第一章规则——商品总额满 4000 分减 500 分。
     * 整单条件需要整单信息，由持有明细集合的 Sale 判断（信息专家原则）。
     */
    public long amountDueCents() {
        long base = totalCents();
        long discount = base >= 4000L ? 500L : 0L;
        return Math.subtractExact(base, discount);
    }

    /** 代码2-22：面向抽象契约调用，不关心具体是哪一种会员卡（多态原则）。 */
    public long finalPriceCents(MemberCard card, long totalCents) {
        return totalCents * card.discountPercent() / 100;
    }

    /** 代码2-25：委托中间对象 TaxAdapter 计算税额（间接原则）。 */
    public long taxCents(long grossCents) {
        return taxAdapter.taxOf(grossCents);
    }

    public SaleStatus getStatus() {
        return status;
    }

    public boolean isClosed() {
        return status == SaleStatus.SETTLED || status == SaleStatus.CANCELLED;
    }

    /** 状态变更归小票自己，外部只能通过方法请求，不能直接改字段。 */
    public void close(boolean paid) {
        if (isClosed()) {
            return;
        }
        this.status = paid ? SaleStatus.SETTLED : SaleStatus.CANCELLED;
    }

    public void markPending(String tradeNo) {
        this.status = SaleStatus.PENDING;
        this.pendingTradeNo = tradeNo;
    }

    public String getPendingTradeNo() {
        return pendingTradeNo;
    }
}
