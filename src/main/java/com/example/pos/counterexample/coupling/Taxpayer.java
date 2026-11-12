// 代码2-11  按控制变量选择税率（反例）——Taxpayer部分
package com.example.pos.counterexample.coupling;

/** 调用方须知道每个标志值的含义。构造方法为配套代码补充，用于设定 flag。 */
@Deprecated
public class Taxpayer {
    private int flag;

    public Taxpayer(int flag) {
        this.flag = flag;
    }

    public long payTax(TaxMaker taxMaker, long taxableCents) {
        return taxMaker.calculate(taxableCents, flag);   // 调用方须知道标志含义
    }
}
