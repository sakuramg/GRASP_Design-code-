// 第二章习题参考答案代码（正文路径：配套代码/chapter02/answer_key/Chapter02Answers.java）
// 覆盖习题2-1、2-6、2-7的计算，2-8、2-9的一种可行设计，2-10的四个结算场景与退款改配。
package com.example.pos.answerkey;

import com.example.pos.board.Action;
import com.example.pos.board.Button;
import com.example.pos.board.SaleBoard;
import com.example.pos.checkout.CheckoutCompletion;
import com.example.pos.checkout.CheckoutController;
import com.example.pos.checkout.PaymentCallback;
import com.example.pos.checkout.PaymentGateway;
import com.example.pos.checkout.PaymentReceipt;
import com.example.pos.checkout.PaymentService;
import com.example.pos.checkout.Printer;
import com.example.pos.member.GoldCard;
import com.example.pos.member.MemberCard;
import com.example.pos.member.NormalCard;
import com.example.pos.member.PlatinumCard;
import com.example.pos.member.SilverCard;
import com.example.pos.sale.Item;
import com.example.pos.sale.Sale;
import com.example.pos.tax.TaxAdapter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class Chapter02Answers {

    private Chapter02Answers() {
    }

    // ===================== 习题2-1 行级促销 =====================

    /** 按信息专家原则，行级促销只改明细的小计方法：商品A买满5件打8折。 */
    public static final class PromoLineItem {
        private final String itemName;
        private final int quantity;
        private final long dealUnitPriceCents;

        public PromoLineItem(Item item, int quantity) {
            this.itemName = item.getName();
            this.quantity = quantity;
            this.dealUnitPriceCents = item.getUnitPriceCents();
        }

        public long subtotalCents() {
            long base = dealUnitPriceCents * quantity;
            if ("A".equals(itemName) && quantity >= 5) {
                return base * 80 / 100;                    // 满5件打8折，int百分数
            }
            return base;
        }
    }

    public static long exercise2_1_subtotalCents() {
        return new PromoLineItem(new Item("A", 1250L), 5).subtotalCents();   // 5000分
    }

    // ===================== 习题2-6 税额拆分 =====================

    /** 固定税率的TaxAdapter，避免依赖全局配置的当前值。 */
    public static TaxAdapter fixedRate(int ratePercent) {
        return new TaxAdapter() {
            @Override
            public int getRate() {
                return ratePercent;
            }
        };
    }

    public static long exercise2_6_taxCents(long grossCents, int ratePercent) {
        return fixedRate(ratePercent).taxOf(grossCents);
    }

    public static long exercise2_6_netCents(long grossCents, int ratePercent) {
        return grossCents - exercise2_6_taxCents(grossCents, ratePercent);
    }

    /** 第（2）问：分子与分母，3%时为26503与206。 */
    public static long[] exercise2_6_integerFormula(long grossCents, int ratePercent) {
        long divisor = 100L + ratePercent;
        long numerator = grossCents * ratePercent * 2 + divisor;
        long denominator = divisor * 2;
        return new long[]{numerator, denominator, numerator / denominator};
    }

    // ===================== 习题2-7 会员折扣 =====================

    public static long[] exercise2_7_discountedCents(long baseCents) {
        Sale sale = new Sale();
        MemberCard[] cards = {new NormalCard(), new SilverCard(), new GoldCard(), new PlatinumCard()};
        long[] result = new long[cards.length];
        for (int i = 0; i < cards.length; i++) {
            result[i] = sale.finalPriceCents(cards[i], baseCents);    // 业务端只依赖MemberCard
        }
        return result;
    }

    // ===================== 习题2-8 汽车主控系统 =====================

    public interface PowerSystem {
        void accelerate(int level);

        int speed();
    }

    public interface BrakeSystem {
        void brake(int level);
    }

    public interface EntertainmentSystem {
        void play(String source);
    }

    /** 主控系统只依赖三个子系统接口，不持有任何部件对象。 */
    public static final class MainControl {
        private final PowerSystem power;
        private final BrakeSystem brake;
        private final EntertainmentSystem entertainment;

        public MainControl(PowerSystem power, BrakeSystem brake, EntertainmentSystem entertainment) {
            this.power = Objects.requireNonNull(power);
            this.brake = Objects.requireNonNull(brake);
            this.entertainment = Objects.requireNonNull(entertainment);
        }

        public void drive(int level) {
            power.accelerate(level);
        }

        public void stop(int level) {
            brake.brake(level);
        }

        public void play(String source) {
            entertainment.play(source);
        }
    }

    /** 单电机动力系统：发动机、差速器、车轮在子系统内部协作。 */
    public static final class SingleMotorPower implements PowerSystem {
        private int speed;

        @Override
        public void accelerate(int level) {
            speed += level;
        }

        @Override
        public int speed() {
            return speed;
        }
    }

    /** 双电机动力系统：只替换实现类，MainControl不变。 */
    public static final class DualMotorPower implements PowerSystem {
        private int frontSpeed;
        private int rearSpeed;

        @Override
        public void accelerate(int level) {
            frontSpeed += level;
            rearSpeed += level;
        }

        @Override
        public int speed() {
            return (frontSpeed + rearSpeed) / 2;
        }
    }

    /** 制动系统管理ABS模块与刹车片；车速只向动力系统读取数值（数据耦合）。 */
    public static final class AbsBrakeSystem implements BrakeSystem {
        private final PowerSystem power;
        private int lastAbsSpeed = -1;

        public AbsBrakeSystem(PowerSystem power) {
            this.power = power;
        }

        @Override
        public void brake(int level) {
            lastAbsSpeed = power.speed();
        }

        public int getLastAbsSpeed() {
            return lastAbsSpeed;
        }
    }

    // ===================== 习题2-9 MyButton重构 =====================

    public interface ButtonAction {
        void execute();
    }

    /** 按钮只持有功能接口，不引用任何具体功能类。 */
    public static final class MyButton {
        private String text = "请点击";
        private ButtonAction clickAction;
        private ButtonAction doubleClickAction;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setClickAction(ButtonAction clickAction) {
            this.clickAction = clickAction;
        }

        public void setDoubleClickAction(ButtonAction doubleClickAction) {
            this.doubleClickAction = doubleClickAction;
        }

        public void click() {
            if (clickAction != null) {
                clickAction.execute();
            }
        }

        public void doubleClick() {
            if (doubleClickAction != null) {
                doubleClickAction.execute();
            }
        }
    }

    /** 装配代码：原有两项功能改由外部注入。 */
    public static MyButton assembleMyButton(List<String> out) {
        MyButton button = new MyButton();
        button.setClickAction(() -> out.add("按钮被点击了"));
        button.setDoubleClickAction(() -> out.add("按钮被双击了"));
        return button;
    }

    // ===================== 习题2-10 结算实验 =====================

    /** 网关测试替身的返回方式。 */
    public enum GatewayMode { SUCCESS, REJECT, PENDING_THEN_SUCCESS, DUPLICATE_SUCCESS }

    /** 待确认后按流水号查询原交易的入口（测试替身）。 */
    public interface TradeQuery {
        PaymentReceipt query(String tradeNo);
    }

    public record ScenarioResult(long amountDueCents, long taxCents, int chargeRequests,
                                 boolean saleClosed, int prints, List<String> messages,
                                 int queries) {
    }

    /** 新增的退款功能类：实现Action，由setAction装配到结算键上。 */
    public static final class RefundAction implements Action {
        private int executed;

        @Override
        public void actionPerformed() {
            executed++;
        }

        public int getExecuted() {
            return executed;
        }
    }

    /** 固定小票：A 1250分×2，B 800分×3。 */
    public static Sale fixedReceipt() {
        Sale sale = new Sale();
        sale.addLineItem(new Item("A", 1250L), 2);
        sale.addLineItem(new Item("B", 800L), 3);
        return sale;
    }

    public static ScenarioResult runScenario(GatewayMode mode) {
        Sale sale = fixedReceipt();
        AtomicInteger charges = new AtomicInteger();
        AtomicInteger prints = new AtomicInteger();
        AtomicInteger queries = new AtomicInteger();

        PaymentGateway gateway = amount -> {
            charges.incrementAndGet();
            switch (mode) {
                case REJECT:
                    throw new IllegalStateException("余额不足");
                case PENDING_THEN_SUCCESS:
                    return PaymentReceipt.pending("TX-P-1");
                default:
                    return PaymentReceipt.success("TX-1");
            }
        };
        TradeQuery tradeQuery = tradeNo -> {
            queries.incrementAndGet();
            return PaymentReceipt.success(tradeNo);
        };
        Printer printer = (s, tax) -> prints.incrementAndGet();
        TaxAdapter taxAdapter = fixedRate(3);
        PaymentService service = new PaymentService(gateway, Runnable::run);   // 同步执行
        CheckoutController controller = new CheckoutController(sale, taxAdapter, printer, service);
        CheckoutCompletion completion = new CheckoutCompletion(controller, Runnable::run);
        PaymentCallback callback = mode == GatewayMode.DUPLICATE_SUCCESS
                ? duplicating(completion) : completion;

        SaleBoard board = new SaleBoard();
        Button settleKey = new Button("S");
        board.addButton(settleKey);
        board.getButton("S").setAction(() -> controller.onSettlePressed(callback));

        List<String> messages = captureStdout(() -> {
            board.getButton("S").onClick();
            if (mode == GatewayMode.PENDING_THEN_SUCCESS && !sale.isClosed()) {
                PaymentReceipt result = tradeQuery.query(sale.getPendingTradeNo());
                if (!result.isPending()) {
                    completion.onSuccess(sale);            // 查询原交易，不发起第二次扣款
                }
            }
        });
        return new ScenarioResult(sale.amountDueCents(), controller.calculateTaxCents(),
                charges.get(), sale.isClosed(), prints.get(), messages, queries.get());
    }

    /** 场景四：同一笔交易的成功通知到达两次。 */
    private static PaymentCallback duplicating(CheckoutCompletion target) {
        return new PaymentCallback() {
            @Override
            public void onSuccess(Sale sale) {
                target.onSuccess(sale);
                target.onSuccess(sale);
            }

            @Override
            public void onFailure(Sale sale, String reason) {
                target.onFailure(sale, reason);
            }

            @Override
            public void onPending(Sale sale, String tradeNo) {
                target.onPending(sale, tradeNo);
            }
        };
    }

    /** 改配：结算键换成退款功能，Button与CheckoutController均不修改。 */
    public static int[] reassignToRefund() {
        AtomicInteger settles = new AtomicInteger();
        SaleBoard board = new SaleBoard();
        board.addButton(new Button("S"));
        board.getButton("S").setAction(settles::incrementAndGet);
        board.getButton("S").onClick();
        RefundAction refund = new RefundAction();
        board.getButton("S").setAction(refund);
        board.getButton("S").onClick();
        return new int[]{settles.get(), refund.getExecuted()};
    }

    private static synchronized List<String> captureStdout(Runnable body) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(ps);
            body.run();
        } finally {
            System.setOut(original);
        }
        List<String> lines = new ArrayList<>();
        for (String line : buffer.toString(StandardCharsets.UTF_8).split("\\R")) {
            if (!line.isBlank()) {
                lines.add(line);
            }
        }
        return lines;
    }

    // ===================== 运行入口 =====================

    public static void main(String[] args) {
        int failures = 0;
        failures += check("2-1 商品A 5件8折行小计", 5000L, exercise2_1_subtotalCents());

        long[] taxes = {128L, 249L, 436L};
        long[] nets = {4272L, 4151L, 3964L};
        int[] rates = {3, 6, 11};
        for (int i = 0; i < rates.length; i++) {
            failures += check("2-6 税率" + rates[i] + "%税额", taxes[i], exercise2_6_taxCents(4400L, rates[i]));
            failures += check("2-6 税率" + rates[i] + "%不含税", nets[i], exercise2_6_netCents(4400L, rates[i]));
        }
        long[] formula = exercise2_6_integerFormula(4400L, 3);
        failures += check("2-6 分子", 26503L, formula[0]);
        failures += check("2-6 分母", 206L, formula[1]);
        failures += check("2-6 整数除法", 128L, formula[2]);

        long[] discounts = exercise2_7_discountedCents(4900L);
        long[] expected = {4900L, 4802L, 4704L, 4655L};
        String[] names = {"普通卡", "银卡", "金卡", "铂金卡"};
        for (int i = 0; i < expected.length; i++) {
            failures += check("2-7 " + names[i], expected[i], discounts[i]);
        }

        PowerSystem dual = new DualMotorPower();
        AbsBrakeSystem abs = new AbsBrakeSystem(dual);
        MainControl control = new MainControl(dual, abs, source -> { });
        control.drive(40);
        control.stop(1);
        failures += check("2-8 双电机下ABS读取车速", 40L, abs.getLastAbsSpeed());

        List<String> out = new ArrayList<>();
        MyButton button = assembleMyButton(out);
        button.click();
        button.doubleClick();
        failures += check("2-9 单击与双击输出", 2L, out.size());

        for (GatewayMode mode : GatewayMode.values()) {
            ScenarioResult r = runScenario(mode);
            System.out.println("2-10 " + mode + "：应付" + r.amountDueCents() + "分，税额" + r.taxCents()
                    + "分，扣款" + r.chargeRequests() + "次，关闭=" + r.saleClosed()
                    + "，打印" + r.prints() + "次，查询" + r.queries() + "次，提示" + r.messages());
            failures += check("2-10 " + mode + " 应付", 4400L, r.amountDueCents());
            failures += check("2-10 " + mode + " 税额", 128L, r.taxCents());
            failures += check("2-10 " + mode + " 扣款次数", 1L, r.chargeRequests());
        }
        int[] reassign = reassignToRefund();
        failures += check("2-10 改配后结算次数", 1L, reassign[0]);
        failures += check("2-10 改配后退款次数", 1L, reassign[1]);

        System.out.println(failures == 0 ? "全部核对通过" : "核对失败项数：" + failures);
        if (failures != 0) {
            System.exit(1);
        }
    }

    private static int check(String label, long expected, long actual) {
        boolean ok = expected == actual;
        System.out.println((ok ? "[通过] " : "[失败] ") + label + "：期望" + expected + "，实际" + actual);
        return ok ? 0 : 1;
    }
}
