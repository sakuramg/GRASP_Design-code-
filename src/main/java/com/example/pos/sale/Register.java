// 代码2-7  创建职责的分配链（正例）——Register部分
package com.example.pos.sale;

/** 收银记账类：负责创建 Sale，不越级创建 SaleLineItem。 */
public class Register {
    public Sale newSale() {
        return new Sale();                    // 小票由收银记账类创建
    }
}
