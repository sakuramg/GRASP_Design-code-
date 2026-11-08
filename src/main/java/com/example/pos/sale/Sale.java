package com.example.pos.sale;

import com.example.pos.member.MemberCard;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售单。掌握明细集合，因此负责汇总（信息专家）；
 * 流水行由它创建（创建者：组合关系）。
 */
public class Sale {

    private final List<SaleLineItem> lineItems = new ArrayList<>();
    private SaleStatus status = SaleStatus.OPEN;
    private String pendingTradeNo;

    /** 流水由小票创建：组合关系，流水不能离开小票单独存在。 */
    public void addLineItem(Item item, int quantity) {
        lineItems.add(new SaleLineItem(item, quantity));
    }

    public List<SaleLineItem> getLineItems() {
        return List.copyOf(lineItems);
    }

    public boolean hasItems() {
        return !lineItems.isEmpty();
    }

    /** 合计：只汇总各行小计，不越级取单价。 */
    public long totalCents() {
        long total = 0L;
        for (SaleLineItem line : lineItems) {
            total = Math.addExact(total, line.subtotalCents());
        }
        return total;
    }

    /** 打折后应付金额：面向 MemberCard 契约调用，不判断卡种（多态）。 */
    /**
     * 整单应付：沿用第一章的规则——商品总额满 4000 分减 500 分，每单一次。
     * 整单条件需要整单信息，因此由持有明细集合的 Sale 自己判断（信息专家）。
     */
    public long amountDueCents() {
        long base = totalCents();
        long discount = base >= 4000L ? 500L : 0L;
        return Math.subtractExact(base, discount);
    }

    public long finalPriceCents(MemberCard card, long totalCents) {
        return totalCents * card.discountPercent() / 100;
    }

    public SaleStatus getStatus() {
        return status;
    }

    /** 状态变更归小票自己，外部只能请求，不能直接改字段。 */
    public void close(boolean paid) {
        if (status != SaleStatus.OPEN) {
            throw new IllegalStateException("当前状态不可关闭：" + status);
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
