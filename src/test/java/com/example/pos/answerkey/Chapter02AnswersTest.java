// 第二章习题参考答案的数值断言（习题2-1、2-6、2-7、2-8、2-9、2-10）
package com.example.pos.answerkey;

import com.example.pos.answerkey.Chapter02Answers.AbsBrakeSystem;
import com.example.pos.answerkey.Chapter02Answers.DualMotorPower;
import com.example.pos.answerkey.Chapter02Answers.GatewayMode;
import com.example.pos.answerkey.Chapter02Answers.MainControl;
import com.example.pos.answerkey.Chapter02Answers.MyButton;
import com.example.pos.answerkey.Chapter02Answers.ScenarioResult;
import com.example.pos.answerkey.Chapter02Answers.SingleMotorPower;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Chapter02AnswersTest {

    @Test
    void exercise2_1_rowPromoSubtotal() {
        assertEquals(5000L, Chapter02Answers.exercise2_1_subtotalCents());
        assertEquals(4900L, Chapter02Answers.fixedReceipt().totalCents());
        assertEquals(4400L, Chapter02Answers.fixedReceipt().amountDueCents());
    }

    @ParameterizedTest
    @CsvSource({"3,128,4272", "6,249,4151", "11,436,3964"})
    void exercise2_6_taxSplit(int rate, long tax, long net) {
        assertEquals(tax, Chapter02Answers.exercise2_6_taxCents(4400L, rate));
        assertEquals(net, Chapter02Answers.exercise2_6_netCents(4400L, rate));
    }

    @Test
    void exercise2_6_integerFormula() {
        assertArrayEquals(new long[]{26503L, 206L, 128L},
                Chapter02Answers.exercise2_6_integerFormula(4400L, 3));
    }

    @Test
    void exercise2_7_memberDiscounts() {
        assertArrayEquals(new long[]{4900L, 4802L, 4704L, 4655L},
                Chapter02Answers.exercise2_7_discountedCents(4900L));
    }

    @Test
    void exercise2_8_dualMotorKeepsMainControl() {
        SingleMotorPower single = new SingleMotorPower();
        MainControl a = new MainControl(single, new AbsBrakeSystem(single), s -> { });
        a.drive(40);
        DualMotorPower dual = new DualMotorPower();
        AbsBrakeSystem abs = new AbsBrakeSystem(dual);
        MainControl b = new MainControl(dual, abs, s -> { });
        b.drive(40);
        b.stop(1);
        assertEquals(single.speed(), dual.speed());
        assertEquals(40, abs.getLastAbsSpeed());
    }

    @Test
    void exercise2_9_myButtonDelegatesToActions() {
        List<String> out = new ArrayList<>();
        MyButton button = Chapter02Answers.assembleMyButton(out);
        assertEquals("请点击", button.getText());
        button.click();
        button.doubleClick();
        assertEquals(List.of("按钮被点击了", "按钮被双击了"), out);
        button.setClickAction(() -> out.add("记账"));          // 换功能不改按钮类
        button.click();
        assertEquals("记账", out.get(2));
        new MyButton().click();                                // 未装配时不抛异常
    }

    @ParameterizedTest
    @EnumSource(GatewayMode.class)
    void exercise2_10_commonFigures(GatewayMode mode) {
        ScenarioResult r = Chapter02Answers.runScenario(mode);
        assertEquals(4400L, r.amountDueCents());
        assertEquals(128L, r.taxCents());
        assertEquals(1, r.chargeRequests());
    }

    @Test
    void exercise2_10_scenario1_success() {
        ScenarioResult r = Chapter02Answers.runScenario(GatewayMode.SUCCESS);
        assertTrue(r.saleClosed());
        assertEquals(1, r.prints());
        assertEquals(List.of("支付成功，应付：4400分"), r.messages());
    }

    @Test
    void exercise2_10_scenario2_reject() {
        ScenarioResult r = Chapter02Answers.runScenario(GatewayMode.REJECT);
        assertFalse(r.saleClosed());
        assertEquals(0, r.prints());
        assertEquals(1, r.messages().size());
        assertTrue(r.messages().get(0).startsWith("支付失败："));
        assertTrue(r.messages().get(0).contains("余额不足"));
    }

    @Test
    void exercise2_10_scenario3_pendingThenQuery() {
        ScenarioResult r = Chapter02Answers.runScenario(GatewayMode.PENDING_THEN_SUCCESS);
        assertEquals(1, r.queries());
        assertTrue(r.saleClosed());
        assertEquals(1, r.prints());
        assertEquals(List.of("等待顾客在手机端确认支付，请稍候", "支付成功，应付：4400分"), r.messages());
    }

    @Test
    void exercise2_10_scenario4_duplicateNotice() {
        ScenarioResult r = Chapter02Answers.runScenario(GatewayMode.DUPLICATE_SUCCESS);
        assertTrue(r.saleClosed());
        assertEquals(1, r.prints());
        assertEquals(List.of("支付成功，应付：4400分"), r.messages());
    }

    @Test
    void exercise2_10_reassignToRefund() {
        assertArrayEquals(new int[]{1, 1}, Chapter02Answers.reassignToRefund());
    }
}
