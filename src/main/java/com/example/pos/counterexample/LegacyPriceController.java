package com.example.pos.counterexample;

import com.example.pos.sale.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码2-10：控制耦合与公共耦合示例（反例，2.3.1）。
 * 内容耦合反例（代码2-11）见同包的 PriceController。
 */
@Deprecated
public class LegacyPriceController {

    // 公共耦合：全局可变集合，任何模块均可读写
    public static final Map<String, Item> GLOBAL_MUTABLE_ALL = new HashMap<>();

    public long calculateTaxByFlag(HardcodedTaxSale sale, long grossCents, int rateFlag) {
        // 控制耦合：调用方须知道每个标志值对应的分支
        return sale.taxCents(grossCents, rateFlag);
    }
}
