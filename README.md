# 第二章配套代码：GRASP设计原则与职责分配

运行方式与代码编号对照以 [README.txt](README.txt) 为准（正文：本章配套代码见配套代码/chapter02，运行方式以README.txt为准）。

```bash
mvn test
```

| 代码编号 | 标题 | 源文件 |
|---|---|---|
| 代码2-1 | 依据第一章代码1-3改写的行小计、汇总与整单优惠 | `src/main/java/com/example/pos/sale/Chapter1ReceiptDemo.java` |
| 代码2-2 | SaleLineItem保存成交价快照 | `src/main/java/com/example/pos/sale/SaleLineItem.java（Item.java）` |
| 代码2-3 | 小票合计的越级计算（反例） | `src/main/java/com/example/pos/counterexample/LegacySale.java` |
| 代码2-4 | Sale委托明细计算小计并汇总（关键代码） | `src/main/java/com/example/pos/sale/Sale.java` |
| 代码2-5 | 小票职责分配的断言 | `src/test/java/com/example/pos/SaleTest.java` |
| 代码2-6 | 在收银机中越级创建明细对象（反例） | `src/main/java/com/example/pos/counterexample/LegacyRegister.java` |
| 代码2-7 | 创建职责的分配链（正例） | `src/main/java/com/example/pos/sale/Register.java、Sale.java` |
| 代码2-8 | 依据第一章代码1-2改写的打印接口与注入 | `src/main/java/com/example/pos/coupling/ReceiptPrinter.java、CheckoutService.java` |
| 代码2-9 | 数据耦合参数传递示例 | `src/main/java/com/example/pos/coupling/ReceiptPrinterService.java` |
| 代码2-10 | 控制耦合与公共耦合示例（反例） | `src/main/java/com/example/pos/counterexample/HardcodedTaxSale.java、LegacyPriceController.java` |
| 代码2-11 | 通过非正常入口修改内部字段（反例） | `src/main/java/com/example/pos/counterexample/PriceController.java` |
| 代码2-12 | 通过接口修改并保留校验（正例） | `src/main/java/com/example/pos/coupling/PriceController.java（Item.changePrice）` |
| 代码2-13 | 承担四种变化原因的结算控制器（反例） | `src/main/java/com/example/pos/counterexample/GodCheckoutController.java` |
| 代码2-14 | 会员模块的受控入口与包内协作（关键代码） | `src/main/java/com/example/pos/member/MemberService.java、AccessPolicy.java、PointsLedger.java` |
| 代码2-15 | 把功能写死在界面控件中（反例） | `src/main/java/com/example/pos/counterexample/HardcodedSettleButton.java` |
| 代码2-16 | 业务对象直接监听外部设备事件（反例·片段） | `src/main/java/com/example/pos/counterexample/BarcodeListener.java、Sale.java` |
| 代码2-17 | 基于抽象事件接收接口的界面解耦（正例） | `src/main/java/com/example/pos/checkout/EventSink.java、SettleButton.java` |
| 代码2-18 | 结算用例的控制器（正例·片段） | `src/main/java/com/example/pos/checkout/CheckoutController.java` |
| 代码2-19 | 按键与功能分离（正例） | `src/main/java/com/example/pos/board/Action.java、Button.java` |
| 代码2-20 | 键位与功能的装配 | `src/main/java/com/example/pos/board/SaleBoard.java` |
| 代码2-21 | 按卡种分支的折扣计算（反例） | `src/main/java/com/example/pos/counterexample/BranchingDiscountSale.java` |
| 代码2-22 | 会员折扣的多态实现（正例） | `src/main/java/com/example/pos/member/MemberCard.java、NormalCard/SilverCard/GoldCard/PlatinumCard.java、sale/Sale.java#finalPriceCents` |
| 代码2-23 | 用标志位选择硬编码税率（反例） | `src/main/java/com/example/pos/counterexample/HardcodedTaxSale.java` |
| 代码2-24 | 税费计算的中间对象（正例） | `src/main/java/com/example/pos/tax/TaxAdapter.java（TaxPolicyConfig.java）` |
| 代码2-25 | Sale依赖TaxAdapter完成税费计算（正例） | `src/main/java/com/example/pos/sale/Sale.java#setTaxAdapter/taxCents` |
| 代码2-26 | 支付结果的回调接口 | `src/main/java/com/example/pos/checkout/PaymentCallback.java` |
| 代码2-27 | 结果处理的实现（片段） | `src/main/java/com/example/pos/checkout/CheckoutCompletion.java` |
| 代码2-28 | 异步支付与回调的登记（片段） | `src/main/java/com/example/pos/checkout/PaymentService.java` |
