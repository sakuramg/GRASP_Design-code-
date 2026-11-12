// 代码2-11  按控制变量选择税率（反例）——TaxMaker部分
package com.example.pos.counterexample.coupling;

/** 控制耦合反例：被调方按 flag 选择分支。5%、7% 为正文教学税率。 */
@Deprecated
public class TaxMaker {
    public long calculate(long taxableCents, int flag) {
        if (flag == 0) {
            return taxableCents * 5 / 100;
        } else if (flag == 1) {
            return taxableCents * 7 / 100;
        } else if (flag == 2) {
            return taxableCents * 7 / 100;
        } else {
            return -1L;                        // 未知标志
        }
    }
}
