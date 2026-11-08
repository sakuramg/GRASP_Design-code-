# 第二章 配套代码：GRASP设计原则与职责分配

本章正文中出现的所有代码清单都在本仓库中，按小节编号组织，可直接编译运行（JDK 17+，JUnit 5）。

## 目录

| 目录 | 对应正文 |
|---|---|
| `src/main/java/com/example/pos/counterexample/` | 代码清单2-1、2-3 对应的越级反例 |
| `src/main/java/com/example/pos/sale/` | 小票、明细、商品（信息专家与创建者） |
| `src/main/java/com/example/pos/member/` | 会员卡多态（2.5.1） |
| `src/main/java/com/example/pos/tax/` | 税费中间对象 TaxAdapter（2.5.2，代码清单2-15） |
| `src/main/java/com/example/pos/checkout/` | 结算控制器与回调（2.4、2.6） |
| `src/test/java/com/example/pos/` | 代码清单2-2 的断言与各小节回归测试 |

## 运行

```bash
mvn test
```

单看小票断言：

```bash
mvn test -Dtest=SaleTest
```

## 与本教材其他章节的关系

- 第一章的 `AmountDemo.java`（49元小票与整单优惠）是本章的起点，本章 `Sale` 与 `SaleLineItem` 沿用其金额单位约定：**整数分，字段与方法以 `Cents` 结尾**。
- 第三章的会员权益扩展、第六章的策略模式会在本章 `MemberCard` 的基础上继续演进。

## 图

本章 9 幅图的可编辑存档见同级目录 `图/单页PPT/`（每幅图一个单页 pptx，形状与文字均可直接修改）；教材中插入的图片由该单页幻灯导出，见 `图/导出图片/`。draw.io 源文件在 `图/` 下。
