// 代码2-25  税费计算的中间类（正例）——按政策扩展的实现类JinIIAdapter（图2-13，正文未给出源码，配套代码补充）
package com.example.pos.tax;

/** 只重写 getRate()：扶持期一般纳税人增值税率6%，为教学税率，实际计税需核实政策。 */
public class JinIIAdapter extends TaxAdapter {
    @Override
    public int getRate() {
        return 6;
    }
}
