《软件设计与体系结构》第二章 GRASP设计原则与职责分配 —— 配套代码
=====================================================================

正文约定：本章配套代码见“配套代码/chapter02”，运行方式以本 README.txt 为准。
本仓库即 chapter02 目录的内容（根目录直接是 Maven 工程，可整体放入 配套代码/chapter02/）。
代码编号与定稿正文一致（代码2-1 至 代码2-28）。

一、环境要求
  - JDK 17 及以上（pom.xml 中 maven.compiler.release=17）
  - Apache Maven 3.8 及以上（首次构建需联网下载 JUnit 5.10.2 与 surefire 3.2.5）
  - 代码2-15、2-17 使用 javax.swing.JButton，测试不弹出窗口，无需图形环境

二、运行方式
  在本目录（含 pom.xml）下执行：
      mvn test                          运行全部单元测试
      mvn test -Dtest=SaleTest          只运行小票基准断言（代码2-5）
      mvn -q compile                    只编译
  测试核对的正文数字：
      行小计 2500 分、2400 分，合计 4900 分，满4000减500后应付 4400 分（SaleTest）
      会员折扣 普通100/银卡98/金卡96/铂金95，4900 分对应 4900/4802/4704/4655 分（MemberCardTest）
      税额 4400×3÷(100+3)≈128.16，按分四舍五入为 128 分（TaxAdapterTest）
      支付三种结果：成功关闭小票并打印、明确拒绝保持未关闭、待确认登记流水号；
      扣款金额为 amountDueCents=4400 分（CheckoutControllerTest）

三、约定
  - 金额一律用 long 整数分，方法与字段以 Cents 结尾；比例一律用 int 百分数，不用 double。
  - 税额＝含税金额×税率÷（100＋税率），按分四舍五入，只在小票上拆分展示，不加到应付金额上。
    3%、6% 为教学税率；实际计税还需核实税种、纳税人资格、商品类别、生效日期、计税基数与法定舍入要求。
  - 反例统一放在 com.example.pos.counterexample 包，标注 @Deprecated，仅供对照，不要在正式代码中调用。

四、代码编号与源文件对照
  代码2-1   依据第一章代码1-3改写的行小计、汇总与整单优惠
          -> src/main/java/com/example/pos/sale/Chapter1ReceiptDemo.java
  代码2-2   SaleLineItem保存成交价快照
          -> src/main/java/com/example/pos/sale/SaleLineItem.java（Item.java）
  代码2-3   小票合计的越级计算（反例）
          -> src/main/java/com/example/pos/counterexample/LegacySale.java
  代码2-4   Sale委托明细计算小计并汇总（关键代码）
          -> src/main/java/com/example/pos/sale/Sale.java
  代码2-5   小票职责分配的断言
          -> src/test/java/com/example/pos/SaleTest.java
  代码2-6   在收银机中越级创建明细对象（反例）
          -> src/main/java/com/example/pos/counterexample/LegacyRegister.java
  代码2-7   创建职责的分配链（正例）
          -> src/main/java/com/example/pos/sale/Register.java、Sale.java
  代码2-8   依据第一章代码1-2改写的打印接口与注入
          -> src/main/java/com/example/pos/coupling/ReceiptPrinter.java、CheckoutService.java
  代码2-9   数据耦合参数传递示例
          -> src/main/java/com/example/pos/coupling/ReceiptPrinterService.java
  代码2-10  控制耦合与公共耦合示例（反例）
          -> src/main/java/com/example/pos/counterexample/HardcodedTaxSale.java、LegacyPriceController.java
  代码2-11  通过非正常入口修改内部字段（反例）
          -> src/main/java/com/example/pos/counterexample/PriceController.java
  代码2-12  通过接口修改并保留校验（正例）
          -> src/main/java/com/example/pos/coupling/PriceController.java（Item.changePrice）
  代码2-13  承担四种变化原因的结算控制器（反例）
          -> src/main/java/com/example/pos/counterexample/GodCheckoutController.java
  代码2-14  会员模块的受控入口与包内协作（关键代码）
          -> src/main/java/com/example/pos/member/MemberService.java、AccessPolicy.java、PointsLedger.java
  代码2-15  把功能写死在界面控件中（反例）
          -> src/main/java/com/example/pos/counterexample/HardcodedSettleButton.java
  代码2-16  业务对象直接监听外部设备事件（反例·片段）
          -> src/main/java/com/example/pos/counterexample/BarcodeListener.java、Sale.java
  代码2-17  基于抽象事件接收接口的界面解耦（正例）
          -> src/main/java/com/example/pos/checkout/EventSink.java、SettleButton.java
  代码2-18  结算用例的控制器（正例·片段）
          -> src/main/java/com/example/pos/checkout/CheckoutController.java
  代码2-19  按键与功能分离（正例）
          -> src/main/java/com/example/pos/board/Action.java、Button.java
  代码2-20  键位与功能的装配
          -> src/main/java/com/example/pos/board/SaleBoard.java
  代码2-21  按卡种分支的折扣计算（反例）
          -> src/main/java/com/example/pos/counterexample/BranchingDiscountSale.java
  代码2-22  会员折扣的多态实现（正例）
          -> src/main/java/com/example/pos/member/MemberCard.java、NormalCard/SilverCard/GoldCard/PlatinumCard.java、sale/Sale.java#finalPriceCents
  代码2-23  用标志位选择硬编码税率（反例）
          -> src/main/java/com/example/pos/counterexample/HardcodedTaxSale.java
  代码2-24  税费计算的中间对象（正例）
          -> src/main/java/com/example/pos/tax/TaxAdapter.java（TaxPolicyConfig.java）
  代码2-25  Sale依赖TaxAdapter完成税费计算（正例）
          -> src/main/java/com/example/pos/sale/Sale.java#setTaxAdapter/taxCents
  代码2-26  支付结果的回调接口
          -> src/main/java/com/example/pos/checkout/PaymentCallback.java
  代码2-27  结果处理的实现（片段）
          -> src/main/java/com/example/pos/checkout/CheckoutCompletion.java
  代码2-28  异步支付与回调的登记（片段）
          -> src/main/java/com/example/pos/checkout/PaymentService.java

五、与正文的差异说明（为可编译而做的最小调整）
  - 代码2-1 的两个 record 在正文中是顶层类型，这里嵌套在 Chapter1ReceiptDemo 中，以免与正式类 Sale、SaleLineItem 重名。
  - 代码2-11 正文直接写 Item 的 unitPriceCents 字段；正式 Item 的字段是 private，
    故反例使用 counterexample.PriceController 内嵌的“字段外露”Item 演示。
  - 代码2-14 中 AccessPolicy 在正文里是包级私有，这里声明为 public，以便包外装配代码传入授权策略；
    PointsLedger 保持包级私有；MemberService 额外提供只读的 pointsOf 便于测试。
  - 代码2-16 是片段，反例 Sale 继承正式 Sale 以复用 addLineItem。
  - 正式 Sale 同时保留 Sale(TaxAdapter) 便捷构造，等价于 new Sale() 后调用 setTaxAdapter（代码2-25）。
  - src/main/resources/frontend/receipt-callback.js 是 2.6.2 正文提到的 Promise 链式回调补充示例，定稿未单列代码编号。
