# 第二章 配套代码：GRASP设计原则与职责分配

本仓库为《软件设计与体系结构》**第二章“GRASP设计原则与职责分配”**的配套源码工程（基于 JDK 17 与 JUnit 5 构建）。本章正文中的 **代码清单2-1 至 代码清单2-28** 均在本仓库中有对应的可编译实现与回归测试。

## 代码清单与源文件一一对应索引

| 代码清单编号 | 清单标题 | 所在小节 | 对应源码文件 |
|---|---|---|---|
| 代码清单2-1 | 第一章代码清单1-3中的行小计、汇总与整单优惠（节选） | 案例导入 | `src/main/java/com/example/pos/sale/Chapter1ReceiptDemo.java` |
| 代码清单2-2 | 正式类SaleLineItem的成交价快照实现 | 案例导入 | `src/main/java/com/example/pos/sale/SaleLineItem.java` |
| 代码清单2-3 | 小票合计的越级计算（反例） | 2.1.1 | `src/main/java/com/example/pos/counterexample/LegacySale.java` |
| 代码清单2-4 | 小票职责分配的断言 | 2.2.1 | `src/test/java/com/example/pos/SaleTest.java` |
| 代码清单2-5 | 在Register中越级创建流水对象（反例） | 2.2.2 | `src/main/java/com/example/pos/counterexample/LegacyRegister.java` |
| 代码清单2-6 | 创建职责的分配链（正例） | 2.2.2 | `src/main/java/com/example/pos/sale/Register.java`、`Sale.java` |
| 代码清单2-7 | 第一章代码清单1-2中的打印契约与注入（节选） | 2.3.1 | `src/main/java/com/example/pos/coupling/ReceiptPrinter.java`、`CheckoutService.java` |
| 代码清单2-8 | 数据耦合参数传递示例 | 2.3.1 | `src/main/java/com/example/pos/coupling/ReceiptPrinterService.java` |
| 代码清单2-9 | 控制耦合标志参数传递示例（反例） | 2.3.1 | `src/main/java/com/example/pos/counterexample/LegacyPriceController.java` |
| 代码清单2-10 | 公共耦合全局可变状态示例（反例） | 2.3.1 | `src/main/java/com/example/pos/counterexample/LegacyPriceController.java` |
| 代码清单2-11 | 通过非正常入口修改内部字段（反例） | 2.3.1 | `src/main/java/com/example/pos/counterexample/LegacyPriceController.java` |
| 代码清单2-12 | 通过接口修改并保留校验（正例） | 2.3.1 | `src/main/java/com/example/pos/coupling/PriceController.java`、`Item.java` |
| 代码清单2-13 | 承担四种变化原因的结算控制器（反例） | 2.3.2 | `src/main/java/com/example/pos/counterexample/GodCheckoutController.java` |
| 代码清单2-14 | 把功能写死在界面控件中（反例） | 2.4.1 | `src/main/java/com/example/pos/counterexample/HardcodedSettleButton.java` |
| 代码清单2-15 | 业务对象直接监听外部设备事件（反例） | 2.4.1 | `src/main/java/com/example/pos/counterexample/HardcodedSettleButton.java` |
| 代码清单2-16 | 基于抽象事件接收接口的界面解耦（正例） | 2.4.1 | `src/main/java/com/example/pos/checkout/EventSink.java`、`SettleButton.java` |
| 代码清单2-17 | 结算用例的控制器（正例·片段） | 2.4.2 | `src/main/java/com/example/pos/checkout/CheckoutController.java` |
| 代码清单2-18 | 按键与功能分离（正例） | 2.5.1 | `src/main/java/com/example/pos/board/Button.java`、`Action.java` |
| 代码清单2-19 | 键位与功能的装配 | 2.5.1 | `src/main/java/com/example/pos/board/SaleBoard.java` |
| 代码清单2-20 | 按卡种分支的折扣计算（反例） | 2.5.1 | `src/main/java/com/example/pos/counterexample/BranchingDiscountSale.java` |
| 代码清单2-21 | 会员折扣的多态实现（正例） | 2.5.1 | `src/main/java/com/example/pos/member/MemberCard.java` 及各卡种子类、`Sale.java` |
| 代码清单2-22 | 用标志位选择硬编码税率（反例） | 2.5.2 | `src/main/java/com/example/pos/counterexample/HardcodedTaxSale.java` |
| 代码清单2-23 | 税费计算的中间对象（正例） | 2.5.2 | `src/main/java/com/example/pos/tax/TaxAdapter.java` |
| 代码清单2-24 | Sale依赖TaxAdapter完成税费计算（正例） | 2.5.2 | `src/main/java/com/example/pos/sale/Sale.java` |
| 代码清单2-25 | 支付结果的回调契约 | 2.6.2 | `src/main/java/com/example/pos/checkout/PaymentCallback.java` |
| 代码清单2-26 | 结果处理的实现（片段） | 2.6.2 | `src/main/java/com/example/pos/checkout/CheckoutCompletion.java` |
| 代码清单2-27 | 异步支付与回调的登记（片段） | 2.6.2 | `src/main/java/com/example/pos/checkout/PaymentService.java` |
| 代码清单2-28 | 前端Promise异步请求与链式回调（示例） | 2.6.2 | `src/main/resources/frontend/receipt-callback.js` |

## 运行单元测试

```bash
mvn test
```

单独运行小票基准断言测试（代码清单2-4）：

```bash
mvn test -Dtest=SaleTest
```

## 金额与命名约定

- 沿用全书统一的整数分约定：所有金额均以整数“分”为单位（`long`），相关字段与方法统一以 `Cents` 结尾，避免浮点数精度误差。
