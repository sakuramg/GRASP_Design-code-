// 代码2-6  在Register中越级创建流水对象（反例）——LegacySale部分
package com.example.pos.counterexample.creator;

import com.example.pos.sale.SaleLineItem;

import java.util.ArrayList;
import java.util.List;

/** 反例，仅供对照。正例见 com.example.pos.sale.Register 与 Sale#addLineItem（代码2-7）。 */
@Deprecated
public class LegacySale {
    private final List<SaleLineItem> lines = new ArrayList<>();

    public void addLineItem(SaleLineItem line) {   // 接收外部创建的流水
        lines.add(line);
    }

    /** 配套代码补充的只读查询，便于测试。 */
    public int lineCount() {
        return lines.size();
    }
}
