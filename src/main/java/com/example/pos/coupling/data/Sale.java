// 代码2-10  向打印机传递数据值
package com.example.pos.coupling.data;

import com.example.pos.sale.SaleLineItem;

import java.util.List;

/**
 * 数据耦合示例：Sale 只把会员号和积分两个值交给打印机。
 * 本例中的 MemberCard（getId/getCredits）与 Printer（print(String)）是本包内的示例类型，
 * 与2.5.1节折扣用的 member.MemberCard、结算用的 checkout.Printer 不是同一个类型。
 * 构造方法为配套代码补充，用于注入三个字段。
 */
public class Sale {
    private List<SaleLineItem> lines;
    private MemberCard member;
    private Printer printer;

    public Sale(List<SaleLineItem> lines, MemberCard member, Printer printer) {
        this.lines = lines;
        this.member = member;
        this.printer = printer;
    }

    public void print() {
        // 只传会员号与积分两个值，不传会员对象
        printer.print("会员号：" + member.getId() + "，积分：" + member.getCredits());
    }
}
