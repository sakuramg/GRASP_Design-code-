// 代码2-4  Sale委托明细计算小计并汇总（关键代码）
// 代码2-7  创建职责的分配链（正例）——Sale部分
// 代码2-22 按卡种分支的会员打折代码（重构前）中的业务端方法 finalPriceCents（重构后一行未改，见代码2-23）
// 代码2-26 Sale依赖TaxAdapter完成税费计算（正例）
package com.example.pos.sale;

import com.example.pos.member.MemberCard;
import com.example.pos.tax.TaxAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售单：持有全部明细，负责汇总与整单优惠（信息专家）、创建明细（创建者）、
 * 委托 TaxAdapter 计税（间接）；状态变更（close/markPending）供2.4、2.6节使用。
 */
public class Sale {

    private final List<SaleLineItem> lineItems = new ArrayList<>();
    private TaxAdapter taxAdapter = new TaxAdapter();
    private SaleStatus status = SaleStatus.OPEN;
    private String pendingTradeNo;

    // ---------- 代码2-4、代码2-7 ----------
    public void addLineItem(Item item, int quantity) {
        // 由Sale统一创建流水，维护它与本小票的归属关系
        SaleLineItem line = new SaleLineItem(item, quantity);
        lineItems.add(line);
    }

    public List<SaleLineItem> getLineItems() {
        return List.copyOf(lineItems);
    }

    public long totalCents() {
        long total = 0L;
        for (SaleLineItem line : lineItems) {
            total = Math.addExact(total, line.subtotalCents());   // 只调用明细的小计契约
        }
        return total;
    }

    public long amountDueCents() {
        long base = totalCents();
        long discount = base >= 4000L ? 500L : 0L;                // 整单条件由Sale判断
        return Math.subtractExact(base, discount);
    }

    // ---------- 代码2-22/2-23 业务端代码 ----------
    // 业务端代码：只调用折扣方法，重构时保持不变
    public long finalPriceCents(MemberCard card, long totalCents) {
        return totalCents * card.discountPercent() / 100;
    }

    // ---------- 代码2-26 ----------
    public void setTaxAdapter(TaxAdapter taxAdapter) {    // 由装配代码替换
        if (taxAdapter == null) {
            throw new IllegalArgumentException("计税适配器不能为空");
        }
        this.taxAdapter = taxAdapter;
    }

    public long taxCents(long grossCents) {
        return taxAdapter.taxOf(grossCents);       // 经中间类动态取得税率
    }

    // ---------- 2.4、2.6 节使用的状态方法 ----------
    public boolean hasItems() {
        return !lineItems.isEmpty();
    }

    public SaleStatus getStatus() {
        return status;
    }

    public boolean isClosed() {
        return status == SaleStatus.SETTLED || status == SaleStatus.CANCELLED;
    }

    /** 状态变更归小票自己：paid 为 true 表示已收款结清，false 表示作废。 */
    public void close(boolean paid) {
        if (isClosed()) {
            return;
        }
        this.status = paid ? SaleStatus.SETTLED : SaleStatus.CANCELLED;
    }

    /** 支付待确认：登记流水号，不按失败处理。 */
    public void markPending(String tradeNo) {
        this.status = SaleStatus.PENDING;
        this.pendingTradeNo = tradeNo;
    }

    public String getPendingTradeNo() {
        return pendingTradeNo;
    }
}
