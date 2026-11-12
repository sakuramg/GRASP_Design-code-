《软件设计与体系结构》第二章 GRASP设计原则与职责分配 —— 配套代码
=====================================================================

正文约定：本章配套代码见“配套代码/chapter02”，运行方式以本 README.txt 为准。
本仓库即 chapter02 目录的内容（根目录直接是 Maven 工程）。
代码编号与定稿正文一致（代码2-1 至 代码2-29）；每个源文件第一行注释标明对应的代码编号。

一、环境要求
  - JDK 17 及以上（pom.xml 中 maven.compiler.release=17）
  - Apache Maven 3.8 及以上（首次构建需联网下载 JUnit 5.10.2 与 surefire 3.2.5）
  - 代码2-17、2-18 使用 javax.swing.JButton；测试以 -Djava.awt.headless=true 运行，不弹出窗口，无需图形环境

二、运行方式
  在本目录（含 pom.xml）下执行：
      mvn test                               运行全部单元测试（共46个，其中习题答案17个）
      mvn test -Dtest=SaleTest               只运行小票断言（代码2-5）
      mvn -q compile                         只编译
      mvn -q compile && java -cp target/classes com.example.pos.answerkey.Chapter02Answers
                                             运行习题参考答案的核对程序
  测试核对的正文数字：
      行小计 2500 分、2400 分，合计 4900 分，满4000减500后应付 4400 分（sale/SaleTest）
      会员折扣 普通100/银卡98/金卡96/铂金95，以4900分为基数得 4900/4802/4704/4655 分（member/MemberCardTest）
      税额 4400×3÷(100+3)≈128.16，按分四舍五入为 128 分，应付仍为 4400 分（tax/TaxAdapterTest）
      支付三种结果：成功关闭销售单并只打印一次（重复通知不再打印）、明确拒绝保持未关闭、
      待确认登记流水号；扣款金额为 amountDueCents=4400 分（checkout/CheckoutCallbackTest）
      settleKey.setListener(refundListener) 换键后按钮代码不变、不再触发结算（ui/PosButtonTest）

三、约定
  - 金额一律用 long 整数分，方法与字段以 Cents 结尾；比例一律用 int 百分数，不用 double。
  - 税额＝含税金额×税率÷（100＋税率），按分四舍五入，只在小票上拆分展示，不加到应付金额上。
    3%、6%、11%（以及代码2-11 的 5%、7%）为教学税率；实际计税还需核实税种、纳税人资格、
    商品类别、生效日期、计税基数与法定舍入要求。
  - 反例放在 com.example.pos.counterexample 及其子包，标注 @Deprecated，仅供对照，不要在正式代码中调用。
  - 正文中与正式类同名的示例类（Sale、SaleLineItem、MemberCard、Item 等）按 Java 规则放在不同的包里。

四、代码编号与源文件对照（路径均相对于 src/main/java/com/example/pos/，测试除外）
  代码2-1   依据第一章代码1-3改写的行小计、汇总与整单优惠    -> sale/Chapter1ReceiptDemo.java
  代码2-2   SaleLineItem保存成交价快照                      -> sale/SaleLineItem.java（商品见 sale/Item.java）
  代码2-3   小票合计的越级计算（反例）                      -> counterexample/expert/Sale.java
  代码2-4   Sale委托明细计算小计并汇总（关键代码）          -> sale/Sale.java
  代码2-5   小票职责分配的断言                              -> src/test/java/com/example/pos/sale/SaleTest.java
  代码2-6   在Register中越级创建流水对象（反例）            -> counterexample/creator/LegacySale.java、counterexample/creator/Register.java
  代码2-7   创建职责的分配链（正例）                        -> sale/Register.java、sale/Sale.java
  代码2-8   依据第一章代码1-2改写的打印接口与注入            -> checkout/Printer.java、checkout/CheckoutService.java
  代码2-9   汽车开动方法的三个版本                          -> coupling/car/Car.java（Engine.java、Wheel.java）
  代码2-10  向打印机传递数据值                              -> coupling/data/Sale.java（MemberCard.java、Printer.java）
  代码2-11  按控制变量选择税率（反例）                      -> counterexample/coupling/TaxMaker.java、counterexample/coupling/Taxpayer.java
  代码2-12  两个类共用全局单例的数据访问对象（关键代码）    -> coupling/common/SaleLineItem.java、coupling/common/DaySumItem.java（ItemDao.java）
  代码2-13  调价时直接修改商品内部字段（反例）              -> counterexample/coupling/PriceController.java（counterexample/coupling/Item.java）
  代码2-14  通过商品公开的方法调价（正例）                  -> coupling/PriceController.java（sale/Item.java#setUnitPriceCents）
  代码2-15  承担四种变化原因的结算控制器（反例）            -> counterexample/GodCheckoutController.java
  代码2-16  会员模块的继承链、包内协作与统一入口（关键代码）-> member/MemberService.java（Member、SilverMember、GoldMember、
                                                               MemberLevelPolicy、AccessPolicy 与正文一样写在同一文件中）
  代码2-17  把功能写死在界面控件中（反例）                  -> counterexample/SettleButton.java
  代码2-18  通过监听接口解除按钮与功能的绑定（正例）        -> ui/PosButton.java、ui/SettleListener.java（ui/RefundListener.java）
  代码2-19  结算用例的控制器（正例·片段）                  -> checkout/CheckoutController.java（sale/ItemCatalog.java）
  代码2-20  按键与功能分离（正例）                          -> board/Action.java、board/Button.java
  代码2-21  收银键盘与键位功能的装配                        -> board/SaleBoard.java（QueryMemberAction、VipPaymentAction、ItemQueryAction）
  代码2-22  按卡种分支的会员打折代码（重构前）              -> counterexample/polymorphism/MemberCard.java、counterexample/polymorphism/Sale.java
  代码2-23  会员打折代码的多态实现（重构后）                -> member/MemberCard.java、NormalCard.java、SilverCard.java、GoldCard.java、PlatinumCard.java
                                                               （业务端 sale/Sale.java#finalPriceCents 未改）
  代码2-24  用标志位选择硬编码税率（反例）                  -> counterexample/HardcodedTaxSale.java
  代码2-25  税费计算的中间类（正例）                        -> tax/TaxAdapter.java（TaxPolicyConfig.java；图2-13 的 JinIIAdapter.java、JinIIIAdapter.java）
  代码2-26  Sale依赖TaxAdapter完成税费计算（正例）          -> sale/Sale.java#setTaxAdapter、taxCents
  代码2-27  支付结果的回调接口                              -> checkout/PaymentCallback.java
  代码2-28  结果处理的实现（片段）                          -> checkout/CheckoutCompletion.java（UiExecutor.java；sale/Sale.java#close、markPending）
  代码2-29  异步支付与回调的登记（片段）                    -> checkout/PaymentService.java（PaymentGateway.java、PaymentReceipt.java）

五、与正文的差异（为可编译、可测试而做的最小调整）
  - 代码2-1：两个 record 在正文中是顶层类型，这里嵌套在 Chapter1ReceiptDemo 中，以免与正式类 Sale、SaleLineItem 重名；
    方法加了 public，以便测试调用。
  - 代码2-2：SaleLineItem 另外公开 getItem()/getQuantity()（正文说明代码2-3反例需要它们）和 getDealUnitPriceCents()。
  - 代码2-4 与 代码2-7：正文两段 Sale.addLineItem 写法不同（一行 lineItems.add(new SaleLineItem(...)) 与先建局部变量 line 再 add），
    效果相同；仓库只有一个 Sale，采用代码2-7的写法。正式 Sale 还合并了代码2-22/2-23 的 finalPriceCents、代码2-26 的计税方法，
    以及 2.4、2.6 节用到的 hasItems、close、markPending、getStatus 等方法。
  - 代码2-3：正文片段没有添加明细的入口，反例 Sale 另加 addLine(SaleLineItem)，仅供测试演示“调价后历史金额改变”。
  - 代码2-6：LegacySale 另加只读的 lineCount()。
  - 代码2-9：正文未给出 Engine、Wheel 的源码，这里补了最小实现；Car 另加构造方法注入发动机与车轮。字段名 K8 与方法名 DM 沿用正文。
  - 代码2-10：正文未给出该例中会员类与打印机的源码，补充 coupling/data/MemberCard（getId、getCredits）与 Printer（print(String)），
    它们与 member.MemberCard（折扣）、checkout.Printer（printReceipt(Sale, long)）不是同一类型；Sale 另加构造方法。
  - 代码2-11：Taxpayer 另加构造方法用来设定 flag。
  - 代码2-12：正文未给出 ItemDao 源码，补充全局单例实现；两个类另加包级 getItem() 供同包测试。
  - 代码2-13：正式 sale.Item 的单价字段是 private，反例改用 counterexample.coupling.Item（单价字段未声明为 private）演示。
  - 代码2-16：MemberService 另加包级私有的 find(memberId)，只供同包测试核对积分，包外不可见。
  - 代码2-18：正文只提到 RefundListener 的名字，未给源码；补充的实现把退款动作注入为 Runnable。
  - 代码2-21：QueryMemberAction、VipPaymentAction、ItemQueryAction 正文未给源码，补充的实现只打印一行提示。
  - 代码2-25：图2-13 的 JinIIAdapter（6%）、JinIIIAdapter（11%）正文只出现在图中，补充为只重写 getRate() 的子类。
  - 代码2-29：PaymentService 只保留正文的 (PaymentGateway, Executor) 构造方法。
  - src/main/resources/frontend/receipt-callback.js 是 2.6 节正文提到的前端 Promise 链式回调补充示例，定稿未单列代码编号。

六、习题参考答案代码（answer_key）
  正文路径：配套代码/chapter02/answer_key/Chapter02Answers.java
  仓库路径：src/main/java/com/example/pos/answerkey/Chapter02Answers.java
           （Maven 工程按包名 com.example.pos.answerkey 存放，与正文路径为同一文件）
  测试路径：src/test/java/com/example/pos/answerkey/Chapter02AnswersTest.java
  对应习题与核对的数字：
      2-1   商品A买满5件打8折，行小计 1250×5×80÷100＝5000 分
      2-6   4400 分在 3%、6%、11% 下税额 128、249、436 分，不含税 4272、4151、3964 分；
            整数写法 26503÷206＝128
      2-7   4900 分按 100/98/96/95 得 4900/4802/4704/4655 分
      2-8   主控系统只依赖 PowerSystem、BrakeSystem、EntertainmentSystem 接口，换双电机实现不改主控系统
      2-9   MyButton 只持有 ButtonAction 接口，单击、双击功能由装配代码注入
      2-10  四个场景应付 4400 分、税额 128 分、各扣款 1 次；成功关闭并打印 1 次；拒绝不关闭、打印 0 次；
            待确认按流水号查询原交易后关闭、打印 1 次；重复成功通知仍只打印 1 次；
            新增 RefundAction 实现 Action，经 setAction 改配，Button 与 CheckoutController 不变
  答案代码只放在 answerkey 包中，与正文示例代码分开，不修改正文代码清单对应的源文件。
