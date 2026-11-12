// 代码2-8  依据第一章代码1-2改写的打印接口与注入——Printer接口
package com.example.pos.checkout;

import com.example.pos.sale.Sale;

/** 打印接口：稳定契约，具体设备由实现类承担。 */
public interface Printer {
    void printReceipt(Sale sale, long taxCents);
}
